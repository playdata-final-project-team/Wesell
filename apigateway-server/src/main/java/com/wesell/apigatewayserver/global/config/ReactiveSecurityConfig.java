package com.wesell.apigatewayserver.global.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wesell.apigatewayserver.global.token.TokenProvider;
import com.wesell.apigatewayserver.response.CustomException;
import com.wesell.apigatewayserver.response.ErrorCode;
import com.wesell.apigatewayserver.response.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@Log4j2
public class ReactiveSecurityConfig {

    private final TokenProvider tokenProvider;

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
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http
                .addFilterAt(authenticationWebFilter(),SecurityWebFiltersOrder.AUTHENTICATION)
                // CSRF 설정
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // CORS 설정
                .cors(ServerHttpSecurity.CorsSpec::disable)
                // 폼 기반 로그인 비활성화
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                // Session STATELESS
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                //exception-handling
                .exceptionHandling(exchange->{
                    exchange
                            .authenticationEntryPoint(serverAuthenticationEntryPoint())
                            .accessDeniedHandler(serverAccessDeniedHandler());
                })
                // 로그아웃 설정
                .logout(logoutSpec -> logoutSpec.logoutUrl("/auth-server/api/v1/logout"))
                
                // 권한 확인 설정
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/admin-service/**").hasRole("ADMIN")
                        .pathMatchers("/auth-server/api/v1/health-check").authenticated()
                        .anyExchange().permitAll()
                );
        return http.build();
    }

    // 인증을 위한 web filter 생성
    private AuthenticationWebFilter authenticationWebFilter(){
        ReactiveAuthenticationManager authenticationManager = Mono::just;

        AuthenticationWebFilter authenticationWebFilter
                = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter
                .setServerAuthenticationConverter(serverAuthenticationConverter());

        return authenticationWebFilter;
    }

    // Authentication 등록
    private ServerAuthenticationConverter serverAuthenticationConverter(){
        return exchange -> {
            String accessToken = tokenProvider.getToken(exchange.getRequest());

            if(!Objects.isNull(accessToken) && tokenProvider.validateToken(accessToken)){
                log.info("authentication 등록 {}",accessToken);
                return Mono.justOrEmpty(tokenProvider.getAuthentication(accessToken));
            }else{
                return Mono.empty();
            }
        };
    }

    // 인증 실패 시 예외 처리
    private ServerAuthenticationEntryPoint serverAuthenticationEntryPoint(){
        return (exchange, exception) -> {
            String requestPath = exchange.getRequest().getPath().value();

            log.error("Unauthorized error: {}", exception.getMessage());
            log.error("Requested path    : {}", requestPath);

            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);

            ErrorResponseDto errorResponseDto =
                    ErrorResponseDto.of(new CustomException(ErrorCode.INVALID_ACCESS_TOKEN));

            try {
                byte[] errorByte = new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .writeValueAsBytes(errorResponseDto);
                DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(errorByte);
                return serverHttpResponse.writeWith(Mono.just(dataBuffer));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
                return serverHttpResponse.setComplete();
            }
        };
    }

    // 접근 권한이 없는 리소스에 접근 시 예외 처리
    private ServerAccessDeniedHandler serverAccessDeniedHandler(){
         return (exchange, exception) -> {
             String requestPath = exchange.getRequest().getPath().value();

             log.error("Forbidden error: {}", exception.getMessage());
             log.error("Requested path    : {}", requestPath);

             ServerHttpResponse serverHttpResponse = exchange.getResponse();
             serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
             serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);

             return serverHttpResponse.setComplete();
         };
    }
}
