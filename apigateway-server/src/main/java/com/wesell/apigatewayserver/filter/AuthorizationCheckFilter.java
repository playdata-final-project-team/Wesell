package com.wesell.apigatewayserver.filter;

import com.wesell.apigatewayserver.response.CustomException;
import com.wesell.apigatewayserver.response.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class AuthorizationCheckFilter extends AbstractGatewayFilterFactory<AuthorizationCheckFilter.Config> {

    private final Environment env;
    private final TokenProperties tokenProperties;
    private final UserDetailsService userDetailsService;

    public AuthorizationCheckFilter(Environment env, TokenProperties tokenProperties,
                                    UserDetailsService userDetailsService) {
        super(Config.class);
        this.env = env;
        this.tokenProperties = tokenProperties;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();
            ServerHttpResponse resp = exchange.getResponse();
            MultiValueMap<String,HttpCookie> cookies = req.getCookies();
            Claims claims = new DefaultClaims();

            //pre-filter
            if(!cookies.isEmpty()){
                if(cookies.containsKey("access-token") && !req.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){

                    String accessToken = cookies.get("access-token").toString();

                    try{
                        claims = Jwts.parser()
                                .setSigningKey(tokenProperties.getSecretKey())
                                .requireIssuer(tokenProperties.getIssuer())
                                .parseClaimsJws(accessToken)
                                .getBody();
                    }catch(ExpiredJwtException exception){
                        throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
                    }catch(Exception e){
                        throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
                    }
                }
            }

            Claims finalClaims = claims;
            return chain.filter(exchange).then(Mono.fromRunnable(()->{

                // ContextHolder에 Context 저장 / Context 삭제
                if(req.getURI().getPath().contains("/sign-in")
                        && resp.getStatusCode() == HttpStatusCode.valueOf(200)){
                    String uuid = finalClaims.getSubject();
                    Authentication authentication =SecurityContextHolder.getContext().getAuthentication();

                    if(authentication ==null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(uuid);
                        authentication = new UsernamePasswordAuthenticationToken(userDetails
                                ,"",userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

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
