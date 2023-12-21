package com.wesell.authenticationserver.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteUserPwCheckRequestDto {

    private String uuid;

    private String pw;
}
