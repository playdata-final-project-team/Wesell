package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.dto.LoginSuccessDto;
import com.wesell.authenticationserver.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.dto.request.LoginUserRequestDto;
import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.response.SuccessCode;
import com.wesell.authenticationserver.service.AuthUserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.HttpHeaders;
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
    @PostMapping("login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginUserRequestDto requestDto){

        log.debug("AuthController - 로그인");

        LoginSuccessDto loginSuccessDto = authUserService.login(requestDto);
        
        log.debug("AuthController - 쿠키 담기");
        String accessToken = loginSuccessDto.getGeneratedTokenDto().getAccessToken();

        ResponseCookie accessTokenCookie = cookieUtil.createTokenCookie(accessToken);

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
                .header(HttpHeaders.SET_COOKIE,savedEmailCookie.toString())
                .body(null);
    }

    // 만료된 토큰 갱신
    @PostMapping("refresh")
    public ResponseEntity<Void> refresh(@CookieValue(name = "access-token") String accessToken){

        LoginSuccessDto loginSuccessDto = authUserService.refreshToken(accessToken);
        String newAccessToken = loginSuccessDto.getGeneratedTokenDto().getAccessToken();
        ResponseCookie tokenCookie = cookieUtil.createTokenCookie(newAccessToken);

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,tokenCookie.toString())
                .body(null);
    }

    // 로그아웃
    @PostMapping("logout")
    public ResponseEntity<?> logout(@CookieValue(name = "access-token") String accessToken){

        authUserService.logout(accessToken);

        ResponseCookie deleteAccessToken = cookieUtil.deleteTokenCookie();

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .body(null);

    }

}
