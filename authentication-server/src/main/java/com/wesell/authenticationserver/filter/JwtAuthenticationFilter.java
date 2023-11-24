package com.wesell.authenticationserver.filter;

import com.wesell.authenticationserver.domain.token.TokenProperties;
import com.wesell.authenticationserver.response.CustomException;
import com.wesell.authenticationserver.response.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProperties tokenProperties;
    private final UserDetailsServiceImpl userDetailsService;
    private boolean isLogout = false;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    FilterChain chain)
            throws ServletException, IOException {

        if(req.getRequestURI().contains("/logout")){
            isLogout = true;
        }

        Cookie[] cookies = req.getCookies();

        if(cookies != null) { // 회원가입 또는 로그인 주기가 긴 회원

            Optional<Cookie> accessCookie = Arrays.stream(cookies)
                    .filter(c -> "access-token".equals(c.getName())).findFirst();

            if(accessCookie.isPresent() && req.getHeader(HttpHeaders.AUTHORIZATION) == null) {

                String accessToken = accessCookie.get().getValue();

                try {

                    Claims claims = Jwts.parser()
                            .setSigningKey(tokenProperties.getSecretKey())
                            .requireIssuer(tokenProperties.getIssuer())
                            .parseClaimsJws(accessToken)
                            .getBody();

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                    if(authentication == null || !authentication.isAuthenticated()) {
                        // 유효한 jwt -> 시큐리티 컨텍스트 홀더에 인증 정보 저장
                        String uuid = claims.getSubject();
                        UserDetails userDetails = userDetailsService.loadUserByUsername(uuid);
                        authentication = new UsernamePasswordAuthenticationToken(userDetails, "",
                                userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                }catch(ExpiredJwtException exception){
                    throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
                }catch(Exception e){
                    throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
                }

            }
        }

        chain.doFilter(req,resp);

        // 로그아웃 성공 시 & refresh-token이 만료된 경우 authentication 삭제 로직 구현
        if(isLogout && resp.getStatus() == HttpStatus.OK.value()
                || resp.getStatus() == HttpStatus.UNAUTHORIZED.value()){
            SecurityContextHolder.clearContext();
        }
    }
}
