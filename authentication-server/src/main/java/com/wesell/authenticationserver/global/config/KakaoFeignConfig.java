package com.wesell.authenticationserver.global.config;

import feign.Client;
import org.springframework.context.annotation.Bean;

public class KakaoFeignConfig {
    @Bean
    public Client feignClient(){
        return new Client.Default(null,null);
    }
}
