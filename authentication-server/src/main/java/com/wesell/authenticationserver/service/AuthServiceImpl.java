package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.controller.dto.request.*;
import com.wesell.authenticationserver.controller.response.ResponseDto;
import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.enum_.Role;
import com.wesell.authenticationserver.domain.repository.AuthUserRepository;
import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.domain.repository.AuthUserSelectRepository;
import com.wesell.authenticationserver.domain.service.AuthService;
import com.wesell.authenticationserver.global.util.SmsUtil;
import com.wesell.authenticationserver.service.dto.feign.AuthUserListFeignResponseDto;
import com.wesell.authenticationserver.global.util.CustomConverter;
import com.wesell.authenticationserver.global.util.CustomPasswordEncoder;
import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import com.wesell.authenticationserver.service.dto.oauth.KakaoAccount;
import com.wesell.authenticationserver.service.dto.oauth.KakaoInfo;
import com.wesell.authenticationserver.service.dto.response.AdminAuthResponseDto;
import com.wesell.authenticationserver.service.dto.response.CreateUserFeignResponseDto;
import com.wesell.authenticationserver.domain.feign.UserServiceFeignClient;
import com.wesell.authenticationserver.service.dto.response.SendSmsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * 회원 인증 인가 정보 관련 기능
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final UserServiceFeignClient userServiceFeignClient;
    private final TokenServiceImpl tokenServiceImpl;
    private final AuthUserRepository authUserRepository;
    private final AuthUserSelectRepository authUserSelectRepository;
    private final CustomPasswordEncoder passwordEncoder;
    private final CustomConverter customConverter;
    private final SmsUtil smsUtil;

    /**
     * 회원 가입 기능
     */
    @Override
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
    @Override
    @Transactional
    public GeneratedTokenDto findOrCreateUser(KakaoInfo kakaoInfo){

        log.debug("소셜 로그인 - 회원 정보 확인");
        KakaoAccount kakaoAccount = kakaoInfo.getKakaoAccount();

        String email = kakaoAccount.getEmail();
        Optional<AuthUser> user = authUserRepository.findByEmail(email);

        if(!user.isPresent()) {
            log.debug("소셜 로그인 - 회원 정보 저장");
            AuthUser newUser = customConverter.toEntity(kakaoAccount);
            newUser = authUserRepository.save(newUser);
            CreateUserFeignResponseDto feignDto = customConverter.toFeignDto(kakaoAccount, newUser.getUuid());
            try {
                userServiceFeignClient.registerUserDetailInfo(feignDto);
                return tokenServiceImpl.generateTokens(newUser);
            }catch(Exception e){
                log.error("유저 서비스로 Feign 요청 시 오류 발생.");
                log.error("detail : {}",e.getMessage());
                throw new CustomException(ErrorCode.USER_SERVICE_FEIGN_ERROR);
            }

        }else {
            return tokenServiceImpl.generateTokens(user.get());
        }

    }

    /**
     * 로그인 기능
     */
    @Override
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
        return tokenServiceImpl.generateTokens(authUser);
    }

    /**
     * 권한 변경 기능
     * @param requestDto
     */
    @Override
    @Transactional
    public void updateRole(ChangeRoleRequestDto requestDto) {

        AuthUser authUser = authUserRepository.findById(requestDto.getUuid()).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        authUser.changeRole(requestDto.getRole());
        authUserRepository.saveAndFlush(authUser);
    }

    /**
     * 강제 탈퇴 여부 변경 기능
     * @param uuid
     * @return
     */
    @Override
    @Transactional
    public void updateIsForced(String uuid) {
        AuthUser authUser = authUserRepository.findById(uuid).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        authUser.changeIsDeleted();
        authUser.changeIsForced();
        authUserRepository.saveAndFlush(authUser);
    }

    /**
     * 회원탈퇴 전 비밀번호 확인 기능
     */
    @Override
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

    /**
     * 회원 탈퇴 기능
     * @param uuid
     */
    @Override
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

    /**
     * 이메일 찾기 전 회원 조회 기능
     * @param phone
     * @return
     */
    @Override
    public SendSmsResponseDto findUserByPhone(String phone) {
        String uuid = userServiceFeignClient.findID(phone);

        if(Objects.isNull(uuid)){
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        String certNum = smsUtil.createCode();
        smsUtil.sendOne(phone,certNum);
        return new SendSmsResponseDto(uuid, certNum);
    }

    /**
     * 이메일 찾기 기능
     * @param uuid
     * @return
     */
    @Override
    public String findEmail(String uuid) {
        return authUserSelectRepository.findEmailByUuid(uuid).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }

    /**
     * 비번 찾기 전 회원 조회 기능
     */
    @Override
    public void findUserByEmail(String email) {
        authUserSelectRepository.findUuidByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }

    /**
     * 비번 변경 기능
     * @param requestDto
     */
    @Override
    public void updatePw(UpdatePwRequestDto requestDto) {
        AuthUser authUser = authUserRepository.findById(requestDto.getUuid()).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        authUser.changePassword(passwordEncoder.encode(requestDto.getPwd()));
        authUserRepository.saveAndFlush(authUser);
    }
    
    /*====================== Feign =======================*/

    // 관리자 페이지 - 회원 전체 목록 조회 기능
    public List<AuthUserListFeignResponseDto> getAllForFeign(){
        List<AuthUser> authUserList = authUserRepository.findAll();
        return customConverter.toFeignDtoList(authUserList);
    }
}
