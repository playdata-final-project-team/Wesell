package com.wesell.authenticationserver.controller.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FindPWDRequestDto {
    private String pwd;
    private String rePwd;
}
