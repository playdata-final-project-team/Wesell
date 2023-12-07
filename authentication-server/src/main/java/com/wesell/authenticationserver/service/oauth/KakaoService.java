package com.wesell.authenticationserver.service.oauth;

import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import com.wesell.authenticationserver.service.dto.oauth.KakaoInfo;
import com.wesell.authenticationserver.service.dto.oauth.KakaoToken;
import com.wesell.authenticationserver.service.feign.KakaoFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Log4j2
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoFeignClient kakaoFeignClient;

    @Value("${kakao.auth-url}")
    private String kakaoAuthUrl;

    @Value("${kakao.user-api-url}")
    private String kakaoUserApiUrl;

    @Value("${kakao.client-id}")
    private String restApiKey;

    @Value("${kakao.redirect-url}")
    private String redirectUrl;

    public KakaoInfo getInfo(String authCode){
        KakaoToken token = getToken(authCode);
        log.debug("token = {}",token);
        try {
            return kakaoFeignClient.getInfo(new URI(kakaoUserApiUrl), token.getTokenType()
            +" "+token.getAccessToken());
        } catch (Exception e) {
            log.error("Error! {}",e.getMessage());
            throw new CustomException(ErrorCode.TEMPORARY_SERVER_ERROR,"kakao 회원 정보 가져오기 중 오류 발생");
        }

    }

    private KakaoToken getToken(String authCode){
        try {
            return kakaoFeignClient.getToken(new URI((kakaoAuthUrl)), restApiKey,
                    redirectUrl, authCode, "authorization_code");
        } catch (Exception e) {
            log.error("Error! {}",e.getMessage());
            KakaoToken.fail();
            throw new CustomException(ErrorCode.TEMPORARY_SERVER_ERROR,"kakao access-token 발급 중 오류 발생");
        }
    }
}
