package com.wesell.adminservice.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListResponseDto {

    private Long id;
    private String name;
    private String nickname;
}
