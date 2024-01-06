package com.wesell.authenticationserver.controller.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatePwRequestDto {
    private String uuid;
    private String pwd;
}