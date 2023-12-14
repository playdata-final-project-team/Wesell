package com.wesell.apigatewayserver.global.token;

import com.wesell.apigatewayserver.response.CustomException;
import com.wesell.apigatewayserver.response.ErrorCode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class TokenProvider {

    private final TokenProperties tokenProperties;

    public String getToken(ServerHttpRequest req){
        HttpCookie cookie = req.getCookies().getFirst("access-token");
        if(!Objects.isNull(cookie)){
            return cookie.getValue();
        }
        return null;
    }

    public boolean validateToken(String accessToken){
        try {
            Jwts.parser().setSigningKey(tokenProperties.getSecretKey())
                    .requireIssuer(tokenProperties.getIssuer())
                    .parseClaimsJws(accessToken);
            return true;
        }catch(SignatureException e){
            log.error("Invalid JWT Signature {}",e.getMessage());
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
        }catch(UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }catch(IllegalArgumentException e){
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }catch(JwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);

        if(claims.get("role") == null){
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("role").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        String uuid = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(uuid,"",authorities);
    }

    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parser()
                    .setSigningKey(tokenProperties.getSecretKey())
                    .requireIssuer(tokenProperties.getIssuer())
                    .parseClaimsJws(accessToken)
                    .getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }

}
