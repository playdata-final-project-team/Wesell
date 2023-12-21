package com.wesell.apigatewayserver.global.config;

import com.wesell.apigatewayserver.global.token.TokenProvider;
import com.wesell.apigatewayserver.response.exception.CustomException;
import com.wesell.apigatewayserver.response.ErrorCode;
import com.wesell.apigatewayserver.response.exception.TokenExpiredException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@AllArgsConstructor
@Log4j2
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private TokenProvider tokenProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if(Objects.isNull(authentication)) {
            log.error("[None access-token] 토큰이 없습니다.");
            return Mono.empty();
        }

        String accessToken = authentication.getCredentials().toString();

        if(tokenProvider.isExpiredToken(accessToken)){
            log.error("[Expired access-token] 만료된 토큰입니다!");
            return Mono.error(TokenExpiredException::new);
        }else if(!tokenProvider.validateToken(accessToken)){
            log.error("[Not Valid access-token] 유효하지 않은 토큰입니다!");
            return Mono.error(new CustomException(ErrorCode.INVALID_ACCESS_TOKEN));
        }

        return Mono.just(authentication);
    }
}
