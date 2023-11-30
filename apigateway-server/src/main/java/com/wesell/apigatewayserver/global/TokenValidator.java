package com.wesell.apigatewayserver.global;

import com.wesell.apigatewayserver.filter.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {

    private final TokenProperties tokenProperties;

    public Claims validateToken(String accessToken) throws Exception{
        return Jwts.parser()
                .setSigningKey(tokenProperties.getSecretKey())
                .requireIssuer(tokenProperties.getIssuer())
                .parseClaimsJws(accessToken)
                .getBody();
    }

}
