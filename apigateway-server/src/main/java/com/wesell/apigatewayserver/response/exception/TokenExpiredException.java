package com.wesell.apigatewayserver.response.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException(){
        super("토큰이 만료되었습니다.");
    }
}
