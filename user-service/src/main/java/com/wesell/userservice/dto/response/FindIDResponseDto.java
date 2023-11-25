package com.wesell.userservice.dto.response;

import com.wesell.userservice.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindIDResponseDto {
    private String uuid;

    public static FindIDResponseDto of(User user) {
        return new FindIDResponseDto(
                user.getUuid()
        );
    }
}
