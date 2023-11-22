package com.wesell.authenticationserver.filter;

import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.response.CustomException;
import com.wesell.authenticationserver.response.ErrorCode;
import com.wesell.authenticationserver.service.token.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomCookie cookieUtil;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    FilterChain chain)
            throws ServletException, IOException {

        // 로그인 요청은 쿠키가 없기 때문에, 이는 권한 확인을 할 필요가 없다.
        if(req.getCookies() != null) {
            Optional<Cookie> cookie = cookieUtil.getJwt(req.getCookies());

            if (cookie.isPresent()) {
                String accessToken = cookie.get().getValue();

                String validResult = tokenProvider.validJwtToken(accessToken);

                if ("pass".equals(validResult)) { // 유효한 jwt -> 시큐리티 컨텍스트 홀더에 인증 정보 저장

                    Authentication authentication = tokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } else if ("expire".equals(validResult)) { // jwt 만료 일을 넘긴 경우 -> 예외 발생하여 client 측에 jwt 재발급 요청 보내도록 조치

                    throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);

                } else { //유효하지 않은 jwt -> 예외 발생하여 client  측에서 재로그인 요청보내도록 조치.

                    throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);

                }
            }
        }

        chain.doFilter(req,resp);

    }
}
