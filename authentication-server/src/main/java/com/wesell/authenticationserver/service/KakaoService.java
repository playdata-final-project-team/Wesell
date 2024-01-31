package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.global.response.error.CustomException;
import com.wesell.authenticationserver.global.response.error.ErrorCode;
import com.wesell.authenticationserver.service.dto.oauth.KakaoInfo;
import com.wesell.authenticationserver.service.dto.oauth.KakaoToken;
import com.wesell.authenticationserver.domain.feign.KakaoFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;

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

    @Value("${kakao.logout-api-url}")
    private String kakaoLogoutApiUrl;

    @Value("${kakao.unlink-api-url}")
    private String kakaoUnlinkApiUrl;

    public KakaoInfo getInfo(String authCode){
        KakaoToken token = getToken(authCode);
        try {
            KakaoInfo kakaoInfo = kakaoFeignClient.getInfo(new URI(kakaoUserApiUrl),
                    token.getTokenType()+" "+token.getAccessToken());
            kakaoInfo.registerToken(token.getAccessToken());
            return kakaoInfo;
        } catch (Exception e) {
            log.error("Error! {}",e.getMessage());
            throw new CustomException(ErrorCode.TEMPORARY_SERVER_ERROR,"kakao 회원 정보 가져오기 중 오류 발생");
        }

    }

    private KakaoToken getToken(String code){
        try {
            return kakaoFeignClient.getToken(new URI(kakaoAuthUrl), restApiKey,
                    redirectUrl, code, "authorization_code");
        } catch (Exception e) {
            log.error("Error! {}",e.getMessage());
            throw new CustomException(ErrorCode.TEMPORARY_SERVER_ERROR,"kakao access-token 발급 중 오류 발생");
        }
    }

    public void logout(String kakaoToken){
        try{
            kakaoFeignClient.logoutOrUnlink(new URI(kakaoLogoutApiUrl),"Bearer "+kakaoToken);
        }catch(Exception e){
            log.error("Error! {}",e.getMessage());
            throw new CustomException(ErrorCode.TEMPORARY_SERVER_ERROR,"kakao 로그아웃 중 오류 발생");
        }
    }

    public void unlink(String kakaoToken){
        try{
            kakaoFeignClient.logoutOrUnlink(new URI(kakaoUnlinkApiUrl),"Bearer "+ kakaoToken);
        }catch(Exception e){
            log.error("Error! {}",e.getMessage());
            throw new CustomException(ErrorCode.TEMPORARY_SERVER_ERROR,"kakao 연결끊기 중 오류 발생");
        }
    }
}
