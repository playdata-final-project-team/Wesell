package com.wesell.authenticationserver.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

// 로그인, 비밀번호 찾기, 마이페이지 들어갈 때 등 공통적으로 password 처리 로직 구현
@Component
@RequiredArgsConstructor
public class CustomPasswordEncoder {
    
    private BCryptPasswordEncoder encoder;

    public String encode(String pw){
        return encoder.encode(pw);
    }

    public boolean matches(String reqPw, String dbPw){
        return encoder.matches(reqPw, dbPw);
    }

}
