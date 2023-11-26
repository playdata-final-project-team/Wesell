package com.wesell.apigatewayserver.global.config;

import com.wesell.apigatewayserver.filter.TokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProperties tokenProperties;
//    private final UserDetailsServiceImpl userDetailsService;

    /**
     * 인증 인가 과정을 거치지 않도록 처리한 설정 - swagger
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers("/v2/api-docs"
                ,"/swagger-resources/**","/swagger-ui.html","/swagger/**");
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http)throws Exception{

        http
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)

                //CORS 설정
                .cors(ServerHttpSecurity.CorsSpec::disable)

                // CSRF 설정
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // 폼 기반 로그인/로그아웃 비활성화
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)

                // 권한 확인 설정 - 추후 세부적으로 구분 예정
                .authorizeExchange(ex -> ex
                        .pathMatchers("/admin-service/*").hasRole("ADMIN")
                        .pathMatchers("/auth-server/health-check").authenticated()
                        .anyExchange().permitAll()
                );

                // 인증/인가 중 예외 발생 시 전달
                // X-Frame-Options 헤더 비활성화 - 보안 측면(디도스 방지)
        return http.build();

    }

}
