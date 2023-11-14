package com.wesell.authenticationserver.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginUserRequestDto {

    private String email;

    private String password;

}
