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
    private String kakaoToken; // 카카오 액세스 토큰

    public static KakaoInfo fail(){
        return null;
    }
}
