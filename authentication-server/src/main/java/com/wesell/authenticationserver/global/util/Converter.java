package com.wesell.authenticationserver.global.util;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.dto.response.CreateUserFeignResponseDto;
import org.springframework.stereotype.Component;

// 기능 : dto <-> entity || dto <-> dto(OpenFeign 시)
@Component
public class Converter {

    /**
     * dto -> entity
     */
    public AuthUser toEntity(CreateUserRequestDto createUserRequestDto){

        String email = createUserRequestDto.getEmail();
        String password = createUserRequestDto.getPw();
        String uuid = createUserRequestDto.getUuid();

        return AuthUser.builder()
                .email(email)
                .password(password)
                .uuid(uuid)
                .build();
    }

    /*---------------------------------------------------------------------------*/

    /**
     * dto -> feignDto
     */

    public CreateUserFeignResponseDto toFeignDto(CreateUserRequestDto createUserRequestDto){
        return CreateUserFeignResponseDto.builder()
                .name(createUserRequestDto.getName())
                .nickname(createUserRequestDto.getNickname())
                .uuid(createUserRequestDto.getUuid())
                .agree(createUserRequestDto.isAgree())
                .phone(createUserRequestDto.getPhone())
                .build();
    }


}
