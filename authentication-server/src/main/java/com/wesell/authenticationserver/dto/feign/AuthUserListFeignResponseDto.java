package com.wesell.authenticationserver.dto.feign;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserListFeignResponseDto {

    private String uuid;
    private String email;
    private String role;
}
