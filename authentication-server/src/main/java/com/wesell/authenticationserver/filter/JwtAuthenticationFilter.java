package com.wesell.authenticationserver.filter;

import com.wesell.authenticationserver.domain.entity.TokenInfo;
import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.response.CustomException;
import com.wesell.authenticationserver.response.ErrorCode;
import com.wesell.authenticationserver.service.AuthUserService;
import com.wesell.authenticationserver.service.TokenInfoService;
import com.wesell.authenticationserver.service.token.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomCookie cookieUtil;
    private final TokenProvider tokenProvider;
    private final TokenInfoService tokenInfoService;
    private  final AuthUserService authUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    FilterChain chain)
            throws ServletException, IOException {

        String accessToken = cookieUtil.getJwt(req.getCookies());
        
        String validResult = tokenProvider.validJwtToken(accessToken);
        
        if("pass".equals(validResult)){ // 유효한 jwt -> 시큐리티 컨텍스트 홀더에 인증 정보 저장

            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }else if("expire".equals(validResult)){ // jwt 만료 일을 넘긴 경우

            TokenInfo tokenInfo = tokenInfoService.getOneByAccessToken(accessToken).orElseThrow(
                    () -> new CustomException(ErrorCode.INVALID_JWT_TOKEN)
                    // 로그아웃 후 재로그인 요청.
            );

            String refreshToken = tokenInfo.getRefreshToken();
            String uuid = tokenInfo.getUuid();

            if(!tokenProvider.validRefreshToken(refreshToken)){
                throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
                // 로그아웃 후 재로그인 요청.
            }

            String newAccessToken = tokenProvider.generateJwt(authUserService.getOneByUuid(uuid));

            TokenInfo tokeninfo = tokenInfoService.saveOrUpdate(null,uuid,null,newAccessToken);


        }else{ //유효하지 않은 jwt
            // 로그아웃 후 재로그인 요청.
        }

        chain.doFilter(req,resp);
    }
}
