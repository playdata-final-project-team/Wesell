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

        // refresh - access 간의 연관 관계를 명시하기 위한 구분 값.
        // 재발급 시, 헤더측 문자열을 비교하여 두 토큰이 관련된 토큰인지 여부를 확인함.
        String sectionId = UUID.randomUUID().toString();

        // 토큰 생성
        String accessToken = createToken(authUser,now, accessTokenExpiry, sectionId);
        String refreshToken = createToken(authUser,now,refreshTokenExpiry, sectionId);

        return new GeneratedTokenDto(authUser.getUuid(),authUser.getRole().toString(),accessToken,refreshToken);

    }

    public String findUuidByRefreshToken(String refreshToken){
        return getClaims(refreshToken).getSubject();
    }

    // refresh-token 검증
    public String validateToken(String refreshToken, String accessToken){

        boolean isExpiredToken = false;

        try{
            getClaims(accessToken);
        }catch(ExpiredJwtException ex){
            isExpiredToken = true;
        }

        // access-token 이 기한 만료된 경우에만 refresh-token 재발급 가능.
        if(!isExpiredToken) throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);

        // access-token 과 refresh-token 간의 연관 관계 확인
        String refreshHeaderParam = refreshToken.split("\\.")[0];
        if(!refreshHeaderParam.equals(accessToken.split("\\.")[0])){
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
    private String createToken(AuthUser authUser, Date now, Date expiration, String sectionId ){
        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setHeaderParam("alg","HS256")
                .setHeaderParam("sectionId", sectionId)
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
