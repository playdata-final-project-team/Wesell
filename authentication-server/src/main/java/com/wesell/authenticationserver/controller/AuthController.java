package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.dto.LoginSuccessDto;
import com.wesell.authenticationserver.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.dto.request.LoginUserRequestDto;
import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.response.SuccessCode;
import com.wesell.authenticationserver.service.AuthUserService;
import com.wesell.authenticationserver.service.token.TokenProvider;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("auth-server")
public class AuthController {

    private final AuthUserService authUserService;
    private final CustomCookie cookieProvider;
    private final TokenProvider tokenProvider;

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

        ResponseCookie cookie = cookieProvider.createTokenCookie(accessToken);

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(null);
    }

    /*===================================== TEST ======================================*/
//    @PostMapping("token-test")
//    public ResponseEntity<GeneratedTokenDto> createTokenTest(@RequestBody LoginUserRequestDto dto){
//        log.debug("Test - 로그인 상황 성공");
//
//        String email = dto.getEmail();
//
//        AuthUser user = authUserService.getOneByEmail(email);
//
//        GeneratedTokenDto generatedTokenDto = tokenProvider.generateToken(user);
//
//
//
//        return ResponseEntity.ok().body(generatedTokenDto);
//    }
}
