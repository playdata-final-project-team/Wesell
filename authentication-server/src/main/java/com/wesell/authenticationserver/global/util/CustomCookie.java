package com.wesell.authenticationserver.global.util;

import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

//JWT를 담을 쿠키 생성 및 기한 만료 기능 구현
@Component
public class CustomCookie {

    // 쿠키 생성
    public ResponseCookie createTokenCookie(String token){

        return ResponseCookie.from("access-token", token)
                .path("/")
                .httpOnly(true)
                .maxAge(60*60)
                .build();
    }

    public ResponseCookie createSaveEmailCookie(String email){

    }

    // 쿠키 무효화
    public ResponseCookie deleteTokenCookie(){
        return ResponseCookie.from("access-token")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
    }

    // 토큰 조회
    public String getJwt(Cookie[] cookies){

        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(c -> "access-token".equals(c.getName())).findFirst();

        return cookie.orElseThrow(() -> new RuntimeException("권한이 없는 접근입니다.")).getValue();

    }

}
