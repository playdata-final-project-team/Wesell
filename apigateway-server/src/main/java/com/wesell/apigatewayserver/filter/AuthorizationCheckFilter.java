package com.wesell.apigatewayserver.filter;

import com.wesell.apigatewayserver.global.TokenValidator;
import com.wesell.apigatewayserver.global.enum_.Role;
import com.wesell.apigatewayserver.response.CustomException;
import com.wesell.apigatewayserver.response.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class AuthorizationCheckFilter extends AbstractGatewayFilterFactory<AuthorizationCheckFilter.Config> {

    private final TokenValidator tokenValidator;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthorizationCheckFilter(TokenValidator tokenValidator, UserDetailsServiceImpl userDetailsService) {
        super(Config.class);
        this.tokenValidator = tokenValidator;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();
            ServerHttpResponse resp = exchange.getResponse();
            MultiValueMap<String,HttpCookie> cookies = req.getCookies();

            //pre-filter
            if(!cookies.isEmpty()){

                List<String> cookieValues = cookies.get("access-token").stream()
                        .filter(c -> "access-token".equals(c.getName())).map(HttpCookie::getValue).toList();

                if(!cookieValues.isEmpty() && req.getHeaders().get(HttpHeaders.AUTHORIZATION) == null){
                    String accessToken = cookieValues.get(0);
                    try{
                        Claims claims = tokenValidator.validateToken(accessToken);

                        Authentication authentication = SecurityContextHolder
                                .getContext().getAuthentication();

                        if(authentication == null) {
                            String uuid = claims.getSubject();
                            String role = claims.get("role",String.class);

                            userDetailsService.registerRole(role);
                            UserDetails userDetails = userDetailsService.loadUserByUsername(uuid);
                            authentication = new UsernamePasswordAuthenticationToken(userDetails
                                    ,"",userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }catch(ExpiredJwtException exception){
                        throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
                    }catch(Exception e){
                        throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
                    }
                }
            }

            return chain.filter(exchange).then(Mono.fromRunnable(()->{

                if(req.getURI().getPath().contains("/logout")
                        && resp.getStatusCode() == HttpStatusCode.valueOf(200)){
                    SecurityContextHolder.clearContext();
                }

            }));
        }));
    }

    @Data
    public static class Config{

    }
}
