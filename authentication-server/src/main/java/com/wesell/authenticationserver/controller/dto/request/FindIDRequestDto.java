package com.wesell.authenticationserver.controller.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FindIDRequestDto {
    private String phoneNumber;
    private String code;
}
