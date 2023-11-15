package com.wesell.authenticationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginSuccessDto {

    private GeneratedTokenDto generatedTokenDto;
}
