package com.wesell.authenticationserver.controller.dto.request;

import com.wesell.authenticationserver.domain.enum_.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuthRoleRequestDto {
    private String uuid;
    private Role role;
}
