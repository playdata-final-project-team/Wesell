package com.wesell.userservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserResponseDto {
    private String name;
    private String nickname;
    private String phone;
    private String uuid;
}
