package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.dto.request.SignInUserRequestDto;
import com.wesell.authenticationserver.dto.response.SignInSuccessResponseDto;
import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.response.CustomException;
import com.wesell.authenticationserver.response.ErrorCode;
import com.wesell.authenticationserver.response.SuccessCode;
import com.wesell.authenticationserver.service.AuthUserService;
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
@RequestMapping("auth-server")
public class AuthController {

    private final AuthUserService authUserService;
    private final CustomCookie cookieUtil;

    // 헬스 체크
    @GetMapping("health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok().body("auth-server available");
    }

    // 회원가입
    @PostMapping("sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody CreateUserRequestDto requestDto){

        log.debug("AuthController - 회원가입");

        authUserService.createUser(requestDto);

        return ResponseEntity
                .status(SuccessCode.USER_CREATED.getStatus())
                .body(null);
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
