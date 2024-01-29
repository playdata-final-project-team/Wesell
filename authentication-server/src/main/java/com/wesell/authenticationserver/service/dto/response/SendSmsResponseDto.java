package com.wesell.authenticationserver.service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendSmsResponseDto {
    private String uuid;
    private String certNum;
}
