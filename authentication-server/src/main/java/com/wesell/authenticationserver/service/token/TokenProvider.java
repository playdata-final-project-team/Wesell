package com.wesell.authenticationserver.service.token;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.token.TokenProperties;
import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class TokenProvider {

    private final TokenProperties tokenProperties;
    static final String X_AUTH_TOKEN = "Bearer";

    /**
     * 토큰 발급 기능
     * @param authUser
     * @return
     */
    public GeneratedTokenDto generateTokens(AuthUser authUser){

        Date now = new Date();

        // accessToken 만료일 - 1시간
        Date accessTokenExpiry = createExpiry(now, tokenProperties.getAccessExpiredTime());

        // refreshToken 만료일 - 1일
        Date refreshTokenExpiry = createExpiry(now,tokenProperties.getRefreshExpiredTime());

        // 토큰 생성
        String accessToken = createToken(authUser,now, accessTokenExpiry);
        String refreshToken = createToken(authUser,now,refreshTokenExpiry);

        return new GeneratedTokenDto(authUser.getUuid(),authUser.getRole().toString(),accessToken,refreshToken);

    }

    public String generatedAccessToken(AuthUser authUser){
        Date now = new Date();
        Date accessTokenExpiry = createExpiry(now, tokenProperties.getAccessExpiredTime());
        return createToken(authUser,now,accessTokenExpiry);
    }

    public String findUuidByRefreshToken(String refreshToken){
        return getClaims(refreshToken).getSubject();
    }

    // refresh-token 검증
    public boolean validateToken(String refreshToken, String accessToken){

        try {
            Claims refTokenClaims = getClaims(refreshToken);
            Claims accTokenClaims = getClaims(accessToken);

            boolean isNotExpired = refTokenClaims.getExpiration().after(new Date());
            boolean isEquals = refTokenClaims.getSubject().equals(accTokenClaims.getSubject());

            return isEquals && isNotExpired;
        }catch(Exception e){
            return false;
        }
        
    }

    public String resolveToken(String bearerToken){
        return bearerToken.substring(X_AUTH_TOKEN.length()).trim();
    }


    // JwtToken -  클라이언트 측에 전달하는 Token 개인정보 O(서명으로 인증)
    private String createToken(AuthUser authUser, Date now, Date expiration ){
        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setHeaderParam("alg","HS256")
                .setSubject(authUser.getUuid())
                .claim("role",authUser.getRole())
                .setIssuer(tokenProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, tokenProperties.getSecretKey())
                .compact();
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(tokenProperties.getSecretKey())
                .requireIssuer(tokenProperties.getIssuer())
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 발급 기능 - 만료일 계산
    private Date createExpiry(Date now, Long expiredAt){

        return new Date(now.getTime() + Duration.ofHours(expiredAt).toMillis());

    }
}
