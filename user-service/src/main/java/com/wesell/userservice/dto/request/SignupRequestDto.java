package com.wesell.userservice.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    private String name;
    private String nickname;
    private String phone;
    private String uuid;
    private boolean agree;
    private boolean isforced;
}