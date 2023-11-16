package com.wesell.adminservice.domain.dto.request;

import com.wesell.adminservice.domain.enum_.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeRoleRequestDto {
    private Role role;
}
