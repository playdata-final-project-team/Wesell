package com.wesell.authenticationserver.service.dto.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoInfo {
    private Long id; // 회원번호
    private KakaoAccount kakaoAccount; // 카카오 계정 정보
    private String accessToken; // 카카오 액세스 토큰

    public void registerToken(String accessToken){
        this.accessToken = accessToken;
    }
}
