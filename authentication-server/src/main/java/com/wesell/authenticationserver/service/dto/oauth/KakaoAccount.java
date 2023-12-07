package com.wesell.authenticationserver.service.dto.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoAccount {
    private Profile profile;
    private String email;
    private String name;
    private String phone_number;
}
