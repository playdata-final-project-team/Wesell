package com.wesell.authenticationserver.service.dto.response;

import com.wesell.authenticationserver.controller.response.ResponseDto;
import com.wesell.authenticationserver.controller.response.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SignInSuccessResponseDto extends ResponseDto {

    private String uuid;

    private String role;

    public SignInSuccessResponseDto(String uuid, String role){
        this.setCode(SuccessCode.OK.getCode());
        this.setStatus(SuccessCode.OK.getStatus().toString());
        this.uuid = uuid;
        this.role = role;
    }

}
