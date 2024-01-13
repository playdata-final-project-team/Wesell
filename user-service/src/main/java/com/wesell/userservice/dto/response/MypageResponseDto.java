package com.wesell.userservice.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MypageResponseDto {
    private String name;
    private String nickname;
    private String phone;
    private String email;
}
