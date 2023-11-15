package com.wesell.authenticationserver.service.token;

import com.wesell.authenticationserver.domain.token.TokenProperties;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class RefreshTokenProvider {

    private final TokenProperties tokenProperties;

    // RefreshToken - 재발급 전용, 개인 정보가 담길 필요 X
    public String createToken(Date now, Date expiration){
        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setHeaderParam("alg","HS256")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, tokenProperties.getSecretKey())
                .compact();
    }

}
