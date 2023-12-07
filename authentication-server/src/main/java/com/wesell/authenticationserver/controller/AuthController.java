package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.controller.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.controller.dto.request.SignInUserRequestDto;
import com.wesell.authenticationserver.controller.response.ResponseDto;
import com.wesell.authenticationserver.service.dto.oauth.KakaoAccount;
import com.wesell.authenticationserver.service.dto.response.SignInSuccessResponseDto;
import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import com.wesell.authenticationserver.controller.response.SuccessCode;
import com.wesell.authenticationserver.service.AuthUserService;
import com.wesell.authenticationserver.service.oauth.KakaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("api/v1")
public class AuthController {

    private final AuthUserService authUserService;
    private final KakaoService kakaoService;
    private final CustomCookie cookieUtil;

    // 헬스 체크
    @GetMapping("health-check")
    public ResponseEntity<ResponseDto> healthCheck(){
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(ResponseDto.of(SuccessCode.OK,"available server"));
    }

    // 회원가입
    @PostMapping("sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody CreateUserRequestDto requestDto){

        log.debug("AuthController - 회원가입");

        return authUserService.createUser(requestDto);
    }

    // 로그인
    @PostMapping("sign-in")
    public ResponseEntity<?> login(@Valid @RequestBody SignInUserRequestDto requestDto){

        log.debug("AuthController - 로그인");

        GeneratedTokenDto generatedTokenDto = authUserService.login(requestDto);

        log.debug("AuthController - 액세스 토큰 쿠키 생성");
        ResponseCookie accessTokenCookie = cookieUtil.createTokenCookie(generatedTokenDto.getAccessToken());

        log.debug("AuthController - 이메일 저장 기능");
        ResponseCookie savedEmailCookie;
        if(requestDto.isSavedEmail()) {
            savedEmailCookie = cookieUtil.createSavedEmailCookie(requestDto.getEmail());
        }else{
            savedEmailCookie = cookieUtil.deleteSavedEmailCookie();
        }

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+generatedTokenDto.getRefreshToken())
                .header(HttpHeaders.SET_COOKIE,accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE,savedEmailCookie.toString())
                .body(new SignInSuccessResponseDto(generatedTokenDto.getUuid(), generatedTokenDto.getRole()));
    }

    // 소셜 로그인 - KAKAO
    @PostMapping("kakao/auth-code")
    public ResponseEntity<?> kakaoAuthCode(@RequestBody String authCode){

        log.debug("소셜 로그인 - 카카오 로그인 ");
        KakaoAccount kakaoAccount = kakaoService.getInfo(authCode).getKakaoAccount();

        log.debug("소셜 로그인 - 회원 확인 및 회원 가입");
        GeneratedTokenDto generatedTokenDto = authUserService.findOrCreateUser(kakaoAccount);

        log.debug("AuthController - 액세스 토큰 쿠키 생성");
        ResponseCookie accessTokenCookie = cookieUtil.createTokenCookie(generatedTokenDto.getAccessToken());

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+generatedTokenDto.getRefreshToken())
                .header(HttpHeaders.SET_COOKIE,accessTokenCookie.toString())
                .body(new SignInSuccessResponseDto(generatedTokenDto.getUuid(), generatedTokenDto.getRole()));
    }

    // 만료된 토큰 갱신
    @PostMapping("refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue(name = "access-token") String accessToken,
            @RequestHeader(value = "Authorization") String refreshToken
            ){
        log.debug("AuthController - 토큰 갱신");
        String newToken = authUserService.refreshToken(refreshToken,accessToken);
        ResponseCookie newTokenCookie = cookieUtil.createTokenCookie(newToken);

        if(newTokenCookie.getValue().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,newTokenCookie.toString())
                .body(null);

    }

    // 로그아웃
    @PostMapping("logout")
    public ResponseEntity<Void> logout(){

        ResponseCookie deleteAccessToken = cookieUtil.deleteTokenCookie();

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .body(null);
    }

}
