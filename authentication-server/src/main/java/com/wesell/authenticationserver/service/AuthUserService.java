package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.enum_.Role;
import com.wesell.authenticationserver.domain.repository.AuthUserRepository;
import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.service.dto.feign.AuthUserListFeignResponseDto;
import com.wesell.authenticationserver.controller.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.controller.dto.request.SignInUserRequestDto;
import com.wesell.authenticationserver.global.util.CustomConverter;
import com.wesell.authenticationserver.global.util.CustomPasswordEncoder;
import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import com.wesell.authenticationserver.service.dto.response.AdminAuthResponseDto;
import com.wesell.authenticationserver.service.dto.response.CreateUserFeignResponseDto;
import com.wesell.authenticationserver.service.feign.UserServiceFeignClient;
import com.wesell.authenticationserver.service.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
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
    private final AuthUserRepository authUserRepository;
    private final CustomPasswordEncoder passwordEncoder;
    private final CustomConverter customConverter;

    /**
     * 회원 가입 기능
     */
    @Transactional
    public ResponseEntity<String> createUser(CreateUserRequestDto createUserRequestDto){
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
        CreateUserFeignResponseDto feignDto = customConverter.toFeignDto(createUserRequestDto);
        return userServiceFeignClient.registerUserDetailInfo(feignDto);
    }

    /**
     * 로그인 기능
     */
    public GeneratedTokenDto login(SignInUserRequestDto requestDto){

        log.debug("로그인 서비스 시작");

        log.debug("이메일로 회원 조회");
        AuthUser authUser = authUserRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.SIGN_IN_FAIL,"가입하지 않은 회원입니다."));

        log.debug("비밀번호 일치 여부 확인");
        if(!passwordEncoder.matches(requestDto.getPassword(), authUser.getPassword())){
            throw new CustomException(ErrorCode.SIGN_IN_FAIL,"비밀번호가 일치하지 않습니다.");
        }

        log.debug("토큰 발급");
        return tokenProvider.generateTokens(authUser);
    }

    // 로그아웃
    public void logout(String accessToken){

    }

    /**
     * refresh jwt 기능
     */
    public String refreshToken(String refreshToken, String accessToken){

        log.debug("토큰 재발급 서비스 시작");
        // Bearer 제거
        refreshToken = tokenProvider.resolveToken(refreshToken);

        log.debug("refresh-token 검증");
        if(tokenProvider.validateToken(refreshToken, accessToken)){

            String uuid = tokenProvider.findUuidByRefreshToken(refreshToken);

            AuthUser authUser = authUserRepository.findById(uuid).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_USER)
            );

            return tokenProvider.generatedAccessToken(authUser);
        }
            return "";

    }

    public AdminAuthResponseDto updateRole(String uuid, Role newRole) {
        Optional<AuthUser> optionalUser = authUserRepository.findById(uuid);
        if (optionalUser.isPresent()) {
            AuthUser user = optionalUser.get();
            user.changeRole(newRole);
            return new AdminAuthResponseDto(uuid + " UUID를 가진 사용자의 권한이 변경되었습니다.");
        } else {
            return new AdminAuthResponseDto(uuid + " UUID를 가진 사용자를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public AdminAuthResponseDto updateIsForced(String uuid) {
        Optional<AuthUser> optionalUser = authUserRepository.findById(uuid);
        if (optionalUser.isPresent()) {
            AuthUser authUser = optionalUser.get();
            authUser.changeIsForced();
            return new AdminAuthResponseDto(uuid + " UUID를 가진 사용자의 강제 탈퇴 여부가 변경되었습니다.");
        } else {
            return new AdminAuthResponseDto(uuid + " UUID를 가진 사용자를 찾을 수 없습니다.");
        }
    }

    /*====================== Feign =======================*/
    public List<AuthUserListFeignResponseDto> getAllForFeign(){
        List<AuthUser> authUserList = authUserRepository.findAll();
        return customConverter.toFeignDto(authUserList);
    }
}
