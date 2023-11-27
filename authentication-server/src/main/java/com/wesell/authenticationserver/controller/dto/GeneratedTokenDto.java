package com.wesell.authenticationserver.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeneratedTokenDto {

    private String uuid;

    private String role;

    private String accessToken;

    private String refreshToken;

}
