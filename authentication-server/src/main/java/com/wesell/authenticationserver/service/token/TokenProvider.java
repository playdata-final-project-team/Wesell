package com.wesell.authenticationserver.service.token;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.token.TokenProperties;
import com.wesell.authenticationserver.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final TokenProperties tokenProperties;
    private final UserDetailsServiceImpl userDetailsService;

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

        return new GeneratedTokenDto(authUser.getUuid(),accessToken,refreshToken);

    }

    // 토큰 재발급 기능


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

    // JWT 유효성 검증 메서드
    public String validJwtToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(tokenProperties.getSecretKey())
                    .requireIssuer(tokenProperties.getIssuer())
                    .parseClaimsJws(token);
            return "pass";
        }catch(ExpiredJwtException exe){
            return "expired";
        }catch (Exception e){
            return "fail";
        }
    }

    // refresh-token 검증 메서드
    public boolean validRefreshToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(tokenProperties.getSecretKey())
                    .requireIssuer(tokenProperties.getIssuer())
                    .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token){

        String uuid = getUuidByToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(uuid);
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());

    }

    public String getUuidByToken(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();

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
