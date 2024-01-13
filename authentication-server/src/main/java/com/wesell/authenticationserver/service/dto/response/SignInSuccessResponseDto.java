package com.wesell.authenticationserver.service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class SignInSuccessResponseDto{

    private String uuid;

    private String role;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long kakaoId;

    public SignInSuccessResponseDto(String uuid, String role, Long kakaoId){
        this.uuid = uuid;
        this.role = role;
        this.kakaoId = kakaoId;
    }

}
