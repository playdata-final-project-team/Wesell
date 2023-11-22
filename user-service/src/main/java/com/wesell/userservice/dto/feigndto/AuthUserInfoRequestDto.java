package com.wesell.userservice.dto.feigndto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserInfoRequestDto {

    private String uuid;
    private String email;
    private String role;

}
