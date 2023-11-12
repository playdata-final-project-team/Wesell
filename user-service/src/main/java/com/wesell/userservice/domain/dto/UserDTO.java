package com.wesell.userservice.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.wesell.userservice.domain.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String nickname;
    private String phone;
    private String uuid;

    @NotNull
    private Boolean agree;
    @NotNull
    private Boolean isforced;
    private LocalDateTime createdAt;


    public static UserDTO toUserDTO(User userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setNickname(userEntity.getNickname());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setUuid(userEntity.getUuid());
        userDTO.setCreatedAt(userEntity.getCreatedAt());

        return userDTO;
    }
}
