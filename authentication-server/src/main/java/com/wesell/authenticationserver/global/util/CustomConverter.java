package com.wesell.authenticationserver.global.util;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.enum_.Role;
import com.wesell.authenticationserver.service.dto.feign.AuthUserListFeignResponseDto;
import com.wesell.authenticationserver.controller.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.service.dto.oauth.KakaoAccount;
import com.wesell.authenticationserver.service.dto.response.CreateUserFeignResponseDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
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

    public AuthUser toEntity(KakaoAccount kakaoAccount){
        String email = kakaoAccount.getEmail();
        String uuid = UUID.randomUUID().toString();

        return AuthUser.builder()
                .email(email)
                .password(null)
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

    public CreateUserFeignResponseDto toFeignDto(KakaoAccount kakaoAccount, String uuid){
        return CreateUserFeignResponseDto.builder()
                .name(kakaoAccount.getName())
                .nickname(kakaoAccount.getProfile().getNickname())
                .uuid(uuid)
                .agree(true)
                .phone(kakaoAccount.getPhone_number())
                .build();
    }

    public List<AuthUserListFeignResponseDto> toFeignDtoList(List<AuthUser> authUserList){
        return authUserList.stream().filter(auth->!auth.isDelete()).map(
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


}
