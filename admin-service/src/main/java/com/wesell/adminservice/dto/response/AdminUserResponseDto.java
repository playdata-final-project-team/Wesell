package com.wesell.adminservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserResponseDto {
    private String name;
    private String nickname;
    private String phone;
    private String uuid;
}