package com.wesell.adminservice.dto.response;

import com.wesell.adminservice.domain.enum_.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListResponseDto {

//    private Long id;
    private String name;
    private String nickname;
    private String phone;
    private String uuid;
    private String email;
    private String role;

}
