package com.wesell.authenticationserver.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInUserRequestDto {

    @NotBlank(message="이메일을 입력해주세요")
    private String email;

    @NotBlank(message="비밀번호를 입력해주세요.")
    private String password;

    private boolean savedEmail;
}
