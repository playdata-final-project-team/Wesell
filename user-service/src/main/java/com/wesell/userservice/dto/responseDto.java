package com.wesell.userservice.dto;

import com.wesell.userservice.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class responseDto {

    private Long id;
    private String name;
    private String nickname;
    private String phone;
    private String uuid;


    public static responseDto of(User user) {
        return new responseDto(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getPhone(),
                user.getUuid()
        );
    }

    public static List<responseDto> of(List<User> userList) {
        return userList.stream()
                .map(user -> new responseDto(
                        user.getId(),
                        user.getName(),
                        user.getNickname(),
                        user.getPhone(),
                        user.getUuid()
                ))
                .collect(Collectors.toList());
    }



}
