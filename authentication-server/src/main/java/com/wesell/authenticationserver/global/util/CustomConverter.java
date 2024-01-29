package com.wesell.authenticationserver.global.util;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.entity.KakaoUser;
import com.wesell.authenticationserver.domain.enum_.Role;
import com.wesell.authenticationserver.service.dto.feign.UserListFeignResponseDto;
import com.wesell.authenticationserver.controller.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.service.dto.oauth.KakaoAccount;
import com.wesell.authenticationserver.service.dto.response.CreateUserFeignResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// 기능 : dto <-> entity || dto <-> dto(OpenFeign 시)
@Component
public class CustomConverter {

    /**
     * dto -> entity
     */
    public AuthUser toEntity(CreateUserRequestDto createUserRequestDto) {

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

    public KakaoUser toEntity(KakaoAccount kakaoAccount) {
        String email = kakaoAccount.getEmail();
        String uuid = UUID.randomUUID().toString();

        return KakaoUser.builder()
                .email(email)
                .uuid(uuid)
                .role(Role.USER)
                .build();
    }

    /*---------------------------------------------------------------------------*/

    /**
     * dto/entity -> feignDto
     */

    public CreateUserFeignResponseDto toFeignDto(CreateUserRequestDto createUserRequestDto) {
        return CreateUserFeignResponseDto.builder()
                .name(createUserRequestDto.getName())
                .nickname(createUserRequestDto.getNickname())
                .uuid(createUserRequestDto.getUuid())
                .agree(createUserRequestDto.isAgree())
                .phone(createUserRequestDto.getPhone())
                .build();
    }

    public CreateUserFeignResponseDto toFeignDto(KakaoAccount kakaoAccount, String uuid) {

        // phone 양식 통일을 위해 문자열 형태 수정 처리함.
        String data = kakaoAccount.getPhone_number();
        String phone = data.trim().replace("+82 ", "0").replaceAll("-", "");

        return CreateUserFeignResponseDto.builder()
                .name(kakaoAccount.getName())
                .nickname(RandomNicknameUtil.makeRandomNickname())
                .uuid(uuid)
                .agree(true)
                .phone(phone)
                .build();
    }

    public List<UserListFeignResponseDto> toFeignDtoList(List<AuthUser> authUserList, List<KakaoUser> kakaoUserList) {
        List<UserListFeignResponseDto> userList = new ArrayList<>();

        for (AuthUser authUser : authUserList) {
            UserListFeignResponseDto dto = UserListFeignResponseDto.builder()
                    .uuid(authUser.getUuid())
                    .role(authUser.getRole().toString())
                    .email(authUser.getEmail())
                    .isDeleted(authUser.isDeleted())
                    .isForced(authUser.isForced())
                    .build();
            userList.add(dto);
        }

        for (KakaoUser kakaoUser : kakaoUserList) {
            UserListFeignResponseDto dto = UserListFeignResponseDto.builder()
                    .uuid(kakaoUser.getUuid())
                    .role(kakaoUser.getRole().toString())
                    .email(kakaoUser.getEmail())
                    .isDeleted(kakaoUser.isDeleted())
                    .isForced(kakaoUser.isForced())
                    .build();
            userList.add(dto);
        }

        return userList;
    }

    /*---------------------------------------------------------------------------*/

    /**
     * entity -> dto
     */
}
