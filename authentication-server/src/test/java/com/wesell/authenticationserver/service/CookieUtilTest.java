package com.wesell.authenticationserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
public class CookieUtilTest {

    private ResponseCookie cookie;

    @BeforeEach
    public void setUpTest(){
        System.out.println("## BeforeEach! ##");

        cookie = ResponseCookie.from("access-token", "a.b.c")
                .path("/")
                .httpOnly(true)
                .maxAge(60*60)
                .build();
    }

    @Test
    @DisplayName("Test : Cookie 정보 확인하기")
    public void checkCookie(){
//        cookie = null;

        System.out.println(cookie.getValue().isBlank());
    }
}
