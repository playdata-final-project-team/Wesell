package com.wesell.authenticationserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * 인증 인가 과정을 거치지 않도록 처리한 설정 - swagger
     * @return return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers("/v2/api-docs"
                ,"/swagger-resources/**","/swagger-ui.html","/swagger/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http
                // CSRF 설정
                .csrf(AbstractHttpConfigurer::disable)

                // JWT 토큰을 사용하기 때문에 Session을 사용하지 않도록 설정
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 권한 확인 설정 - 추후 세부적으로 구분 예정
                .authorizeHttpRequests(authorizationConfig->{
                    authorizationConfig.anyRequest().permitAll();
                })

                // 인증/인가 중 예외 발생 시 전달

                // X-Frame-Options 헤더 비활성화 - 보안 측면(디도스 방지)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                );

        return http.build();
    }

}
