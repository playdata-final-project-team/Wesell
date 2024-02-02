package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.controller.dto.request.*;
import com.wesell.authenticationserver.domain.service.AuthService;
import com.wesell.authenticationserver.domain.service.TokenService;
import com.wesell.authenticationserver.global.response.success.SuccessApiResponse;
import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.global.util.SmsUtil;
import com.wesell.authenticationserver.service.dto.oauth.KakaoInfo;
import com.wesell.authenticationserver.service.dto.response.SendSmsResponseDto;
import com.wesell.authenticationserver.service.dto.response.SignInSuccessResponseDto;
import com.wesell.authenticationserver.global.response.success.SuccessCode;
import com.wesell.authenticationserver.service.KakaoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("api/v2")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final KakaoService kakaoService;
    private final CustomCookie cookieUtil;
    private final SmsUtil smsUtil;

    // 회원가입
    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserRequestDto requestDto) {
        log.info("client 회원가입 POST 요청");
        authService.createUser(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessApiResponse.of(SuccessCode.USER_CREATED));
    }

    // 로그인
    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInUserRequestDto requestDto) {
        log.info("client 로그인 POST 요청");
        GeneratedTokenDto generatedTokenDto = authService.signIn(requestDto);

        log.debug("AuthController - 액세스 토큰 쿠키 생성");
        ResponseCookie accessTokenCookie = cookieUtil.createAccessTokenCookie(generatedTokenDto.getAccessToken());
        ResponseCookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(generatedTokenDto.getRefreshToken());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(SuccessApiResponse.of(
                        SuccessCode.OK,
                        new SignInSuccessResponseDto(generatedTokenDto.getUuid(),
                                generatedTokenDto.getRole(),
                                null
                                ))
                );
    }

    // 소셜 로그인 - KAKAO
    @PostMapping("kakao/auth-code")
    public ResponseEntity<?> kakaoAuthCode(@RequestBody String authCode, HttpServletRequest request) {
        log.info("client 카카오 로그인 POST 요청");
        KakaoInfo kakaoInfo = kakaoService.getInfo(authCode);

        log.debug("소셜 로그인 - 액세스 토큰 정보 session 에 저장");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(kakaoInfo.getId().toString(), kakaoInfo.getAccessToken());

        log.debug("소셜 로그인 - 회원 확인 및 회원 가입");
        GeneratedTokenDto generatedTokenDto = authService.findOrCreateUser(kakaoInfo);

        log.debug("AuthController - 쿠키 생성");
        ResponseCookie accessTokenCookie = cookieUtil.createAccessTokenCookie(generatedTokenDto.getAccessToken());
        ResponseCookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(generatedTokenDto.getRefreshToken());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(SuccessApiResponse.of(
                        SuccessCode.OK,
                        new SignInSuccessResponseDto(generatedTokenDto.getUuid(),
                                generatedTokenDto.getRole(),
                                kakaoInfo.getId())
                ));
    }

    // 소셜 로그아웃 - KAKAO
    @GetMapping("kakao/logout")
    public ResponseEntity<?> kakaoLogout() {
        log.info("client 카카오 로그아웃 GET 요청");

        log.debug("쿠키 삭제");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();
        ResponseCookie deleteJSESSIONID = cookieUtil.deleteJSESSIONIDCookie();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteJSESSIONID.toString())
                .body(SuccessApiResponse.of(SuccessCode.OK));
    }

    // 만료된 토큰 갱신
    @GetMapping("refresh")
    public ResponseEntity<?> refresh(
            @CookieValue(name = "access-token", required = false) String accessToken,
            @CookieValue(name = "refresh-token") String refreshToken
    ) {

        log.debug("client 토큰 갱신 GET 요청");
        GeneratedTokenDto generatedTokenDto = tokenService.refreshToken(refreshToken, accessToken);

        ResponseCookie newAccessTokenCookie = cookieUtil.createAccessTokenCookie(generatedTokenDto.getAccessToken());
        ResponseCookie newRefreshTokenCookie = cookieUtil.createRefreshTokenCookie(generatedTokenDto.getRefreshToken());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, newAccessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, newRefreshTokenCookie.toString())
                .body(SuccessApiResponse.of(SuccessCode.OK));
    }

    // 로그아웃
    @GetMapping("logout")
    public ResponseEntity<?> logout() {
        log.debug("client 로그아웃 GET 요청");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .body(SuccessApiResponse.of(SuccessCode.OK));
    }

    // 회원탈퇴 전 비밀번호 확인
    @PostMapping("delete/pw-check")
    public ResponseEntity<?> checkPw(@RequestBody DeleteUserPwCheckRequestDto requestDto) {
        log.debug("client 회원 탈퇴 전 비밀번호 확인 POST 요청");
        authService.checkPwForDelete(requestDto);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }

    // 회원 탈퇴
    @DeleteMapping("delete/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "uuid") String uuid) {
        log.debug("client 회원탈퇴 DELETE 요청");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();

        authService.deleteUser(uuid);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .body(SuccessApiResponse.of(SuccessCode.OK));
    }

    // 카카오 회원 탈퇴
    @DeleteMapping("delete/kakao")
    public ResponseEntity<?> deleteKakaoUser(@RequestParam String kakaoId,
                                             @RequestParam String uuid,
                                             HttpServletRequest request) {
        log.debug("client 카카오 회원탈퇴 요청");
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        boolean isValidJsessionId = false;

        if(cookies != null && session != null){
            Optional<Cookie> jsessionCookie =  Arrays.stream(cookies).filter((c) -> c.getName().equals("JSESSIONID")).findFirst();
            if(jsessionCookie.isPresent()){
                Cookie jCookie = jsessionCookie.get();
                isValidJsessionId = jCookie.getValue().equals(session.getId());

                if(isValidJsessionId){
                    log.info("회원탈퇴 싫행!");
                    String accessToken = (String) session.getAttribute(kakaoId);
                    kakaoService.unlink(accessToken);
                    session.invalidate();
                }
            }
        }else{
            log.info("cookie or session is null");
        }
        
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();
        ResponseCookie deleteJSESSIONID = cookieUtil.deleteJSESSIONIDCookie();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteJSESSIONID.toString())
                .body(SuccessApiResponse.of(SuccessCode.OK));
    }

    // 회원가입 시 인증 번호 요청
    @GetMapping("sign-up/phone/validate")
    public ResponseEntity<?> sendCertNumber(@RequestParam String phoneNumber){
        log.info("client 인증 번호 요청 - 회원가입");
        String certNum = smsUtil.createCode();

        smsUtil.sendOne(phoneNumber, certNum);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK, certNum));
    }

    // 휴대전화 인증 번호 요청
    @GetMapping("phone/validate")
    public ResponseEntity<?> sendCertNumberWithUuid(@RequestParam String phoneNumber) {
        log.info("client 인증 번호 요청");

        log.debug("휴대전화 번호로 회원 조회");
        String uuid = authService.findUserByPhone(phoneNumber);
        String certNum = smsUtil.createCode();

        smsUtil.sendOne(phoneNumber, certNum);
        SendSmsResponseDto respDto = SendSmsResponseDto.builder()
                .uuid(uuid)
                .certNum(certNum)
                .build();
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK, respDto));
    }

    // 이메일 찾기 - 이메일 반환
    @GetMapping("find-email/{uuid}")
    public ResponseEntity<?> findEmail(@PathVariable("uuid") String uuid) {
        log.info("client 이메일 요청");

        String email = authService.findEmail(uuid);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK, email));
    }

    // 비밀번호 찾기 - 이메일 존재 여부 확인
    @GetMapping("email-check")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        authService.findUserByEmail(email);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }

    // 이메일 중복 조회
    @GetMapping("dup-check")
    public ResponseEntity<?> dupCheckEmail(@RequestParam("email") String email) {
        authService.checkEmail(email);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }

    // 비밀번호 찾기 - 비밀번호 수정
    @PostMapping("update-pw")
    public ResponseEntity<?> updatePWD(@RequestBody UpdatePwRequestDto requestDto) {
        authService.updatePw(requestDto);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }

}