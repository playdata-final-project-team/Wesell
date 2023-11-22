package com.wesell.userservice.dto.feigndto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminFeignResponseDto {

    private String uuid;
    private String email;
    private String role;
    private String nickname;
    private String phone;
    private String name;
}
