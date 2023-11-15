package com.wesell.authenticationserver.service.token;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.token.TokenProperties;
import com.wesell.authenticationserver.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.service.TokenInfoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final RefreshTokenProvider refreshTokenProvider;
    private final AccessTokenProvider accessTokenProvider;
    private final TokenInfoService tokenInfoService;
    private final TokenProperties tokenProperties;

    /**
     * 토큰 만료일 지정 및 db 저장
     * @param authUser
     * @return
     */
    public GeneratedTokenDto generateToken(AuthUser authUser){

        Date now = new Date();

        // accessToken 만료일 - 1시간
        Date accessTokenExpiry = createExpiry(now, tokenProperties.getAccessExpiredTime());

        // refreshToken 만료일 - 1일
        Date refreshTokenExpiry = createExpiry(now,tokenProperties.getRefreshExpiredTime());

        // 토큰 생성
        String accessToken = accessTokenProvider.createToken(authUser,now, accessTokenExpiry);
        String refreshToken = refreshTokenProvider.createToken(now,refreshTokenExpiry);
        String uuid = authUser.getUuid();

        return new GeneratedTokenDto(refreshToken,accessToken,uuid);
    }

    // Claims 반환 - 호출하는 곳에서 예외처리
    private Claims getClaims(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(tokenProperties.getSecretKey())
                .requireIssuer(tokenProperties.getIssuer())
                .parseClaimsJws(token)
                .getBody();
    }

    // 만료일 계산
    private Date createExpiry(Date now, Long expiredAt){
        return new Date(now.getTime() + Duration.ofHours(expiredAt).toMillis());
    }
}
