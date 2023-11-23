package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.entity.TokenInfo;
import com.wesell.authenticationserver.domain.repository.AuthUserRepository;
import com.wesell.authenticationserver.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.dto.LoginSuccessDto;
import com.wesell.authenticationserver.dto.feign.AuthUserListFeignResponseDto;
import com.wesell.authenticationserver.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.dto.request.LoginUserRequestDto;
import com.wesell.authenticationserver.global.util.CustomConverter;
import com.wesell.authenticationserver.global.util.CustomPasswordEncoder;
import com.wesell.authenticationserver.response.CustomException;
import com.wesell.authenticationserver.response.ErrorCode;
import com.wesell.authenticationserver.service.feign.UserServiceFeignClient;
import com.wesell.authenticationserver.service.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 회원 인증 인가 정보 관련 기능
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class AuthUserService {

    private final UserServiceFeignClient userServiceFeignClient;
    private final TokenProvider tokenProvider;
    private final TokenInfoService tokenInfoService;
    private final AuthUserRepository authUserRepository;
    private final CustomPasswordEncoder passwordEncoder;
    private final CustomConverter customConverter;

    /**
     * 회원 가입 기능
     */
    @Transactional
    public void createUser(CreateUserRequestDto createUserRequestDto){
        log.debug("회원 가입 시작");

        log.debug("uuid 생성, 비밀번호 암호화, 회원 인증 정보 엔티티로 convert");
        String pw = passwordEncoder.encode(createUserRequestDto.getPw()); // 암호화
        String uuid = UUID.randomUUID().toString();
        createUserRequestDto.setUuid(uuid);
        createUserRequestDto.setPw(pw);
        AuthUser user = customConverter.toEntity(createUserRequestDto);

        log.debug("회원 인증 정보 - DB 저장");
        authUserRepository.save(user);

        // 연동 전 테스트를 위해 주석처리
        log.debug("User-Service Api Call - 회원가입 요청");
//        CreateUserFeignResponseDto feignDto = converter.toFeignDto(createUserRequestDto);
//        userServiceFeignClient.registerUserDetailInfo(feignDto);
    }

    /**
     * 로그인 기능
     * @param dto
     * @return
     */
    public LoginSuccessDto login(LoginUserRequestDto requestDto){

        log.debug("로그인 서비스 시작");

        log.debug("이메일로 회원 조회");
        AuthUser authUser = authUserRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_SIGNUP_USER));

        log.debug("비밀번호 일치 여부 확인");
        if(!passwordEncoder.matches(requestDto.getPassword(), authUser.getPassword())){
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        }

        GeneratedTokenDto generatedTokenDto = tokenProvider.generateTokens(authUser);

        TokenInfo tokenInfo = tokenInfoService.saveOrUpdate(generatedTokenDto, generatedTokenDto.getUuid(),
                null,null);

        return new LoginSuccessDto(customConverter.toDto(tokenInfo));
    }

    public AuthUser getOneByUuid(String uuid){
        return authUserRepository.findByUuid(uuid).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_SIGNUP_USER)
        );
    }

    /*====================== Feign =======================*/
    public List<AuthUserListFeignResponseDto> getAllForFeign(){
        List<AuthUser> authUserList = authUserRepository.findAll();
        return customConverter.toFeignDto(authUserList);
    }
}
