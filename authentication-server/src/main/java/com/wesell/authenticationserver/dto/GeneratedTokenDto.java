package com.wesell.authenticationserver.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedTokenDto {

    // 토큰 생성 시 전달용 DTO(서버 내부)
    private String refreshToken;
    private String accessToken;
    private String uuid;

}