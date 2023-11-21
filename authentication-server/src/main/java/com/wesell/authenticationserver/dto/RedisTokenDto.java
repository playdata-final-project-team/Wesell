package com.wesell.authenticationserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class RedisTokenDto {

    String tokenType;

    String accessToken;

    String refreshToken;

    Integer expiresIn;

    Integer refreshTokenExpiresIn;

    String scope;

}
