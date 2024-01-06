package com.wesell.authenticationserver.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SendSmsResponseDto {
    private String uuid;
    private String certNum;
}
