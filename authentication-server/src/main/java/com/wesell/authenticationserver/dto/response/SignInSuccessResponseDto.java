package com.wesell.authenticationserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInSuccessResponseDto {

    private String uuid;

    private String role;

}
