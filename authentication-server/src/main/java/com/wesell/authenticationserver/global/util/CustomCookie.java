package com.wesell.authenticationserver.global.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

//JWT를 담을 쿠키 생성 및 기한 만료 기능 구현
@Component
public class CustomCookie {

    // access-token 쿠키 생성
    public ResponseCookie createAccessTokenCookie(String token){

        return ResponseCookie.from("access-token", token)
                .path("/")
                .httpOnly(true)
                .maxAge(60*60+(60*30))
                .build();
    }

    // refresh-token 쿠키 생성
    public ResponseCookie createRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from("refresh-token",refreshToken)
                .path("/")
                .httpOnly(true)
                .maxAge(60*60*24+(60*30))
                .build();
    }

    // 로그인 시 이메일 저장 기능 쿠키 생성.
    public ResponseCookie createSavedEmailCookie(String email){

        return ResponseCookie.from("savedEmail",email)
                .path("/")
                .maxAge(60 * 60 *24)
                .build();
    }

    // 쿠키 무효화
    public ResponseCookie deleteAccessTokenCookie(){
        return ResponseCookie.from("access-token")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
    }

    public ResponseCookie deleteRefreshTokenCookie(){
        return ResponseCookie.from("refresh-token")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
    }

    public ResponseCookie deleteSavedEmailCookie(){
        return ResponseCookie.from("savedEmail")
                .path("/")
                .maxAge(0)
                .build();
    }
}
