package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.controller.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.controller.dto.request.DeleteUserPwCheckRequestDto;
import com.wesell.authenticationserver.controller.dto.request.SignInUserRequestDto;
import com.wesell.authenticationserver.controller.response.ResponseDto;
import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.global.util.SmsUtil;
import com.wesell.authenticationserver.service.AuthUserService;
import com.wesell.authenticationserver.service.dto.oauth.KakaoAccount;
import com.wesell.authenticationserver.service.dto.oauth.KakaoInfo;
import com.wesell.authenticationserver.service.dto.response.SignInSuccessResponseDto;
import com.wesell.authenticationserver.controller.response.SuccessCode;
import com.wesell.authenticationserver.service.oauth.KakaoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("api/v1")
public class AuthController {

    private final AuthUserService authUserService;
    private final KakaoService kakaoService;
    private final CustomCookie cookieUtil;
    private final SmsUtil smsUtil;

    // 헬스 체크
    @GetMapping("health-check")
    public ResponseEntity<ResponseDto> healthCheck(){
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(ResponseDto.of(SuccessCode.OK,"available server"));
    }

    // 회원가입
    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserRequestDto requestDto){

        log.debug("AuthController - 회원가입");

        return authUserService.createUser(requestDto);
    }

    // 로그인
    @PostMapping("sign-in")
    public ResponseEntity<?> login(@Valid @RequestBody SignInUserRequestDto requestDto){

        log.debug("AuthController - 로그인");

        GeneratedTokenDto generatedTokenDto = authUserService.login(requestDto);

        log.debug("AuthController - 액세스 토큰 쿠키 생성");
        ResponseCookie accessTokenCookie = cookieUtil.createAccessTokenCookie(generatedTokenDto.getAccessToken());
        ResponseCookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(generatedTokenDto.getRefreshToken());

        log.debug("AuthController - 이메일 저장 기능");
        ResponseCookie savedEmailCookie;
        if(requestDto.isSavedEmail()) {
            savedEmailCookie = cookieUtil.createSavedEmailCookie(requestDto.getEmail());
        }else{
            savedEmailCookie = cookieUtil.deleteSavedEmailCookie();
        }

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE,savedEmailCookie.toString())
                .body(new SignInSuccessResponseDto(generatedTokenDto.getUuid(), generatedTokenDto.getRole(),null));
    }

    // 소셜 로그인 - KAKAO
    @PostMapping("kakao/auth-code")
    public ResponseEntity<?> kakaoAuthCode(@RequestBody String authCode, HttpSession session){

        log.debug("소셜 로그인 - 카카오 로그인 ");
        KakaoInfo kakaoInfo = kakaoService.getInfo(authCode);

        log.debug("소셜 로그인 - 액세스 토큰 정보 session 에 저장");
        session.setAttribute(kakaoInfo.getId().toString(),kakaoInfo.getKakaoToken());

        log.debug("소셜 로그인 - 회원 확인 및 회원 가입");
        GeneratedTokenDto generatedTokenDto = authUserService.findOrCreateUser(kakaoInfo);

        log.debug("AuthController - 쿠키 생성");
        ResponseCookie accessTokenCookie = cookieUtil.createAccessTokenCookie(generatedTokenDto.getAccessToken());
        ResponseCookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(generatedTokenDto.getRefreshToken());

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(new SignInSuccessResponseDto(generatedTokenDto.getUuid(), generatedTokenDto.getRole(),kakaoInfo.getId()));
    }

    // 소셜 로그아웃 - KAKAO
    @GetMapping("kakao/logout/{kakaoId}")
    public ResponseEntity<?> kakaoLogout(@PathVariable("kakaoId") String kakaoId, HttpSession session){
        log.debug("소셜 로그인 - 카카오 로그아웃");
        Object token = session.getAttribute(kakaoId);

        if(!Objects.isNull(token)) {
            kakaoService.logout(token.toString());
        }
        session.removeAttribute(kakaoId);

        log.debug("쿠키 삭제");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();
        ResponseCookie deleteJSESSIONID = cookieUtil.deleteJSESSIONIDCookie();

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteJSESSIONID.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    // 만료된 토큰 갱신
    @GetMapping("refresh")
    public ResponseEntity<?> refresh(
            @CookieValue(name = "access-token", required = false) String accessToken,
            @CookieValue(name = "refresh-token") String refreshToken
            ){

        log.debug("AuthController - 토큰 갱신");
        GeneratedTokenDto generatedTokenDto = authUserService.refreshToken(refreshToken,accessToken);

        ResponseCookie newAccessTokenCookie = cookieUtil.createAccessTokenCookie(generatedTokenDto.getAccessToken());
        ResponseCookie newRefreshTokenCookie = cookieUtil.createRefreshTokenCookie(generatedTokenDto.getRefreshToken());

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,newAccessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE,newRefreshTokenCookie.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    // 로그아웃
    @GetMapping("logout")
    public ResponseEntity<?> logout(){
        log.debug("AuthController - 로그아웃");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    // 회원탈퇴 전 비밀번호 확인
    @PostMapping("delete/pw-check")
    public ResponseEntity<ResponseDto> checkPw(@RequestBody DeleteUserPwCheckRequestDto requestDto){
        log.debug("AuthController - 회원 탈퇴 전 비밀번호 확인");
        authUserService.checkPwForDelete(requestDto);
        return ResponseEntity.ok(ResponseDto.of(SuccessCode.OK));
    }

    // 회원 탈퇴
    @DeleteMapping("delete/{uuid}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable(value="uuid") String uuid){
        log.debug("AuthController - 회원 탈퇴");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();

        authUserService.deleteUser(uuid);

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE,deleteRefreshToken.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    // 카카오 회원 탈퇴
    @DeleteMapping("delete/kakao")
    public ResponseEntity<ResponseDto> deleteKakaoUser(@RequestParam String kakaoId,
                                                       @RequestParam String uuid,
                                                       HttpSession session){
        log.debug("AuthController - 카카오 회원 탈퇴");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();
        kakaoService.unlink(session.getAttribute(kakaoId).toString());

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE,deleteRefreshToken.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    // 번호 인증
    @GetMapping("/phone/validate")
    public ResponseEntity<?> sendSMSById(@RequestParam String phoneNumber) {

        String numStr = smsUtil.createCode();
        System.out.println(phoneNumber);

        smsUtil.sendOne(phoneNumber,numStr);

        return ResponseEntity.ok(phoneNumber);
    }
}