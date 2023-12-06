package com.wesell.authenticationserver.service.dto.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoInfo {
    private KakaoAccount kakaoAccount;

    public static KakaoInfo fail(){
        return null;
    }
}
