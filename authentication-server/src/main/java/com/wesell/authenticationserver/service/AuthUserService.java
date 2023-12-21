package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.controller.dto.request.DeleteUserPwCheckRequestDto;
import com.wesell.authenticationserver.controller.response.ResponseDto;
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
import com.wesell.authenticationserver.service.dto.oauth.KakaoAccount;
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
    public ResponseEntity<ResponseDto> createUser(CreateUserRequestDto createUserRequestDto){
        log.debug("회원 가입 시작");

        log.debug("uuid 생성, 비밀번호 암호화, 회원 인증 정보 엔티티로 convert");
        String uuid = UUID.randomUUID().toString();
        String pw = passwordEncoder.encode(createUserRequestDto.getPw()); // 암호화
        createUserRequestDto.setUuid(uuid);
        createUserRequestDto.setPw(pw);
        AuthUser user = customConverter.toEntity(createUserRequestDto);

        log.debug("회원 인증 정보 - DB 저장");
        authUserRepository.save(user);

        // 연동 전 테스트를 위해 주석처리
        log.debug("User-Service Api Call - 회원가입 요청");
        try {
            CreateUserFeignResponseDto feignDto = customConverter.toFeignDto(createUserRequestDto);
            return userServiceFeignClient.registerUserDetailInfo(feignDto);
        }catch(Exception e){
            log.error("유저 서비스로 Feign 요청 시 오류 발생.");
            log.error("detail : {}",e.getMessage());
            throw new CustomException(ErrorCode.USER_SERVICE_FEIGN_ERROR);
        }
    }

    /**
     *  소셜 로그인 - 가입된 회원 여부 확인 후 저장
     */
    @Transactional
    public GeneratedTokenDto findOrCreateUser(KakaoAccount kakaoAccount){

        log.debug("소셜 로그인 - 회원 정보 확인");
        String email = kakaoAccount.getEmail();
        Optional<AuthUser> user = authUserRepository.findByEmail(email);

        if(!user.isPresent()) {
            log.debug("소셜 로그인 - 회원 정보 저장");
            AuthUser newUser = customConverter.toEntity(kakaoAccount);
            newUser = authUserRepository.save(newUser);
            CreateUserFeignResponseDto feignDto = customConverter.toFeignDto(kakaoAccount, newUser.getUuid());
            try {
                userServiceFeignClient.registerUserDetailInfo(feignDto);
                return tokenProvider.generateTokens(newUser);
            }catch(Exception e){
                log.error("유저 서비스로 Feign 요청 시 오류 발생.");
                log.error("detail : {}",e.getMessage());
                throw new CustomException(ErrorCode.USER_SERVICE_FEIGN_ERROR);
            }

        }else {
            return tokenProvider.generateTokens(user.get());
        }

    }

    /**
     * 로그인 기능
     */
    public GeneratedTokenDto login(SignInUserRequestDto requestDto){

        log.debug("로그인 서비스 시작");

        log.debug("이메일로 회원 조회");
        AuthUser authUser = authUserRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        log.debug("비밀번호 일치 여부 확인");
        if(!passwordEncoder.matches(requestDto.getPassword(), authUser.getPassword())){
            throw new CustomException(ErrorCode.NOT_CORRECT_PASSWORD);
        }

        log.debug("토큰 발급");
        return tokenProvider.generateTokens(authUser);
    }

    /**
     * refresh jwt 기능
     */
    public GeneratedTokenDto refreshToken(String refreshToken, String accessToken){

        log.debug("토큰 재발급 서비스 시작");
        String uuid = tokenProvider.validateToken(refreshToken, accessToken);

        log.debug("refresh-token 검증");
        if( uuid != null) {
            AuthUser authUser = authUserRepository.findById(uuid).orElseThrow(
                    () -> new CustomException(ErrorCode.INVALID_REFRESH_TOKEN)
            );

            return tokenProvider.generateTokens(authUser);
        }else{
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    /**
     * 권한 변경 기능
     * @param uuid
     * @param newRole
     * @return
     */
    @Transactional
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

    /**
     *
     * @param uuid
     * @return
     */
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

    @Transactional
    public AdminAuthResponseDto updateIsDeleted(String uuid) {
        Optional<AuthUser> optionalUser = authUserRepository.findById(uuid);

        if (optionalUser.isPresent()) {
            AuthUser authUser = optionalUser.get();
            authUser.changeIsDeleted();
            return new AdminAuthResponseDto(uuid + " UUID를 가진 사용자의 삭제 여부가 변경되었습니다.");
        } else {
            return new AdminAuthResponseDto(uuid + " UUID를 가진 사용자를 찾을 수 없습니다.");
        }
    }

    public void checkPwForDelete(DeleteUserPwCheckRequestDto requestDto){
        log.debug("회원 탈퇴 전 비밀번호 확인");

        log.debug("회원 조회");
        String uuid = requestDto.getUuid();
        AuthUser authUser = authUserRepository.findById(uuid).orElseThrow(()->{
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        });

        log.debug("비밀번호 일치 여부");
        String inputData = requestDto.getPw();
        String pw = authUser.getPassword();
        if(!passwordEncoder.matches(inputData,pw)){
            throw new CustomException(ErrorCode.NOT_CORRECT_PASSWORD);
        }
    }

    public void deleteUser(String uuid){
        log.debug("회원 탈퇴");

        log.debug("탈퇴할 회원 조회");
        AuthUser authUser = authUserRepository.findById(uuid).orElseThrow(() ->{
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        });

        log.debug("탈퇴한 회원으로 변경 - DB 반영");
        authUser.changeIsDeleted();
        authUserRepository.saveAndFlush(authUser);

        log.debug("User-Service Api Call - 회원가입 요청");
        try {
            userServiceFeignClient.deleteUser(uuid);
        }catch(Exception e){
            log.error("유저 서비스로 Feign 요청 시 오류 발생.");
            log.error("detail : {}",e.getMessage());
            throw new CustomException(ErrorCode.USER_SERVICE_FEIGN_ERROR);
        }

    }

    /*====================== Feign =======================*/
    public List<AuthUserListFeignResponseDto> getAllForFeign(){
        List<AuthUser> authUserList = authUserRepository.findAll();
        return customConverter.toFeignDtoList(authUserList);
    }
}
