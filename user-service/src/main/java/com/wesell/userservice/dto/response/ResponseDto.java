package com.wesell.userservice.dto.response;

import com.wesell.userservice.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private String name;
    private String nickname;
    private String phone;
    private String uuid;

    public static ResponseDto of(User user) {
        return new ResponseDto(
                user.getName(),
                user.getNickname(),
                user.getPhone(),
                user.getUuid()
        );
    }

    public static List<ResponseDto> of(List<User> userList) {
        return userList.stream()
                .map(user -> new ResponseDto(
                        user.getName(),
                        user.getNickname(),
                        user.getPhone(),
                        user.getUuid()
                ))
                .collect(Collectors.toList());
    }
}
