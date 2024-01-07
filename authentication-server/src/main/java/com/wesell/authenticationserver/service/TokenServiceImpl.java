package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.enum_.RefreshStatus;
import com.wesell.authenticationserver.domain.repository.AuthUserRepository;
import com.wesell.authenticationserver.domain.service.TokenService;
import com.wesell.authenticationserver.global.token.TokenProperties;
import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class TokenServiceImpl implements TokenService {

    private final TokenProperties tokenProperties;
    private final AuthUserRepository authUserRepository;

    /**
     * 토큰 발급 기능
     * @param uuid
     * @param role
     * @return
     */
   
    @Override
    public GeneratedTokenDto generateTokens(String uuid, String role) {
        Date now = new Date();

        // accessToken 만료일 - 1시간
        Date accessTokenExpiry = createExpiry(now, tokenProperties.getAccessExpiredTime());

        // refreshToken 만료일 - 1일
        Date refreshTokenExpiry = createExpiry(now,tokenProperties.getRefreshExpiredTime());

        // 토큰 생성
        String accessToken = createToken(uuid,role,now, accessTokenExpiry);
        String refreshToken = createToken(uuid, role,now,refreshTokenExpiry);

        return new GeneratedTokenDto(uuid,role,accessToken,refreshToken);
    }

    /**
     * refresh jwt 기능
     */
    @Override
    public GeneratedTokenDto refreshToken(String refreshToken, String accessToken) {

        log.debug("토큰 재발급 서비스 시작");
        RefreshStatus status = validateToken(refreshToken, accessToken);

        if(status == RefreshStatus.NORMAL){
            log.debug("액세스 토큰 쿠키 만료 전 재발급 요청옴");
            String uuid = getClaims(accessToken).getSubject();
            String role = getClaims(accessToken).get("role").toString();
            return generateTokens(uuid, role);
        }else if(status == RefreshStatus.NEED){
            log.debug("액세스 토큰 쿠키 만료 후 재발급 요청옴");
            String uuid = getClaims(refreshToken).getSubject();
            String role = getClaims(refreshToken).get("role").toString();
            return generateTokens(uuid, role);
        }else{
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    // refresh-token 검증
    @Override
    public RefreshStatus validateToken(String refreshToken, String accessToken){

        // access-token null인 경우, 만료된 경우가 많기 때문에, 이 경우, 새로운 access-token 발급 및 refresh-token을 발급한다.
        if(Objects.isNull(accessToken) && Objects.nonNull(refreshToken)){
           return RefreshStatus.NEED;
        }

        if(Objects.isNull(refreshToken)){
            return RefreshStatus.ABNORMAL;
        }

        // 만료 여부 확인
        if(getClaims(accessToken).getExpiration().before(new Date()) ||
        getClaims(refreshToken).getExpiration().before(new Date())) {
            return RefreshStatus.ABNORMAL;
        }

        // access-token 과 refresh-token 간의 연관 관계 확인
        Date refreshCreatedAt = getClaims(refreshToken).getIssuedAt();
        Date accessCreatedAt = getClaims(accessToken).getIssuedAt();
        if(!refreshCreatedAt.equals(accessCreatedAt)){
           return RefreshStatus.ABNORMAL;
        }

        return RefreshStatus.NORMAL;
    }

    // JwtToken -  클라이언트 측에 전달하는 Token 개인정보 O(서명으로 인증)
    private String createToken(String uuid, String role, Date now, Date expiration){
        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setHeaderParam("alg","HS256")
                .setSubject(uuid)
                .claim("role",role)
                .setIssuer(tokenProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, tokenProperties.getSecretKey())
                .compact();
    }

    // 토큰 parsing
    private Claims getClaims(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(tokenProperties.getSecretKey())
                    .requireIssuer(tokenProperties.getIssuer())
                    .parseClaimsJws(token)
                    .getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }catch (Exception e){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    // 토큰 만료일 계산
    private Date createExpiry(Date now, Long expiredAt){
        return new Date(now.getTime() + Duration.ofHours(expiredAt).toMillis());
    }
}
