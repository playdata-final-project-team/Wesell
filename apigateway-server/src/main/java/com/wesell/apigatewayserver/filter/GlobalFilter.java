package com.wesell.apigatewayserver.filter;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter(){
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest req= exchange.getRequest();
            ServerHttpResponse resp = exchange.getResponse();

            log.info("## Global Filter Message : {}",config.getMessage());

            if(config.isShowPreLogger()){
                log.info("## Global Filter Start : request uri -> {}",req.getURI().getPath());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if(config.isShowPostLogger()){
                    log.info("## Global Filter End: response code -> {}",resp.getStatusCode());
                }
            }));

        });
    }

    @Data
    public static class Config{
        private String message;
        private boolean showPreLogger;
        private boolean showPostLogger;
    }
}
