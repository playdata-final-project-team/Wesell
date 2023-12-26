package com.wesell.authenticationserver.service.token;

import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.token.TokenProperties;
import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class TokenProvider {

    private final TokenProperties tokenProperties;

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

    // refresh-token 검증
    public String validateToken(String refreshToken, String accessToken){

        if(Objects.isNull(accessToken)){
            return null;
        }

        // access-token 만료여부 재확인
        if(!getClaims(accessToken).getExpiration().before(new Date())){
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        // access-token 과 refresh-token 간의 연관 관계 확인
        Date refreshCreatedAt = getClaims(refreshToken).getIssuedAt();
        Date accessCreatedAt = getClaims(accessToken).getIssuedAt();

        if(!refreshCreatedAt.equals(accessCreatedAt)){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // refresh-token 검증
        try {
            return getClaims(refreshToken).getSubject();
        }catch(Exception e){
            return null;
        }
    }

    // refresh-token 단독 검증하기
    public String validateRefreshToken(String refreshToken){
        if(Objects.isNull(refreshToken)){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if(getClaims(refreshToken).getExpiration().before(new Date())){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // refresh-token 검증
        try {
            return getClaims(refreshToken).getSubject();
        }catch(Exception e){
            return null;
        }
    }

    // JwtToken -  클라이언트 측에 전달하는 Token 개인정보 O(서명으로 인증)
    private String createToken(AuthUser authUser, Date now, Date expiration){
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
        try {
            return Jwts.parser()
                    .setSigningKey(tokenProperties.getSecretKey())
                    .requireIssuer(tokenProperties.getIssuer())
                    .parseClaimsJws(token)
                    .getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }

    // 토큰 발급 기능 - 만료일 계산
    private Date createExpiry(Date now, Long expiredAt){

        return new Date(now.getTime() + Duration.ofHours(expiredAt).toMillis());

    }
}
