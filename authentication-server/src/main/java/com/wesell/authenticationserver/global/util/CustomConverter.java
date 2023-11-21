package com.wesell.authenticationserver.global.util;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.entity.TokenInfo;
import com.wesell.authenticationserver.domain.enum_.Role;
import com.wesell.authenticationserver.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.dto.feign.AuthUserListFeignResponseDto;
import com.wesell.authenticationserver.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.dto.response.CreateUserFeignResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 기능 : dto <-> entity || dto <-> dto(OpenFeign 시)
@Component
public class CustomConverter {

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
                .role(Role.USER)
                .build();
    }

    /*---------------------------------------------------------------------------*/

    /**
     * dto/entity -> feignDto
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

    public List<AuthUserListFeignResponseDto> toFeignDto(List<AuthUser> authUserList){
        return authUserList.stream().map(
                user->AuthUserListFeignResponseDto.builder()
                        .uuid(user.getUuid())
                        .email(user.getEmail())
                        .role(user.getRole().toString())
                        .build()
        ).collect(Collectors.toList());
    }

    /*---------------------------------------------------------------------------*/

    /**
     * entity -> dto
     */

    // TokenInfo -> GeneratedDto : 토큰 정보를 controller 로 전달 하기 위한 전처리.
    public GeneratedTokenDto toDto(TokenInfo tokenInfo){
        return GeneratedTokenDto.builder()
                .uuid(tokenInfo.getUuid())
                .refreshToken(tokenInfo.getRefreshToken())
                .accessToken(tokenInfo.getAccessToken())
                .build();
    }

}
