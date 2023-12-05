package com.wesell.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserRequestDto {
    private String name;
    private String nickname;
    private String phone;
    private String uuid;
}