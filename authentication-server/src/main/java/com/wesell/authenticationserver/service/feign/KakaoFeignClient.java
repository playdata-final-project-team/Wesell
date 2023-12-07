package com.wesell.authenticationserver.service.feign;

import com.wesell.authenticationserver.global.config.KakaoFeignConfig;
import com.wesell.authenticationserver.service.dto.oauth.KakaoInfo;
import com.wesell.authenticationserver.service.dto.oauth.KakaoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name= "kakaoFeignClient", configuration = KakaoFeignConfig.class )
public interface KakaoFeignClient {

    @PostMapping
    KakaoInfo getInfo(URI baseUrl, @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);

    @PostMapping
    KakaoToken getToken(URI baseUrl, @RequestParam("client_id") String restApiKey,
                        @RequestParam("redirect_url") String redirectUrl,
                        @RequestParam("code") String code,
                        @RequestParam("grant_type") String grantType);
}
