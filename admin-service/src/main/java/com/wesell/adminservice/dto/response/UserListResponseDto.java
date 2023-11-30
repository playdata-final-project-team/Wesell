package com.wesell.adminservice.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListResponseDto {

    private String name;
    private String nickname;
    private String phone;
    private String uuid;
}
