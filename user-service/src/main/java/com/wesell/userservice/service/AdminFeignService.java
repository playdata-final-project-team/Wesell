package com.wesell.userservice.service;

import com.wesell.userservice.dto.feigndto.AdminFeignResponseDto;
import com.wesell.userservice.dto.feigndto.AuthUserInfoRequestDto;
import com.wesell.userservice.dto.response.ResponseDto;
import com.wesell.userservice.exception.UserNotFoundException;
import com.wesell.userservice.service.feign.AuthServerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminFeignService {

    private final AuthServerFeignClient authServerFeignClient;
    private final UserService userService;

    public List<AdminFeignResponseDto> getAllForFeign() {

        try {
            List<ResponseDto> userInfoList = userService.findUsers();


            List<AuthUserInfoRequestDto> authUserInfoList = authServerFeignClient.getListAuthUserInfo();

            List<AdminFeignResponseDto> responseDtoList = userInfoList.stream().map(user ->
                    AdminFeignResponseDto.builder()
                            .uuid(user.getUuid())
                            .name(user.getName())
                            .phone(user.getPhone())
                            .nickname(user.getNickname())
                            .build()
            ).toList();

            for(AdminFeignResponseDto dto : responseDtoList){
                String uuid = dto.getUuid();

                for(AuthUserInfoRequestDto authDto : authUserInfoList){
                    if(authDto.getUuid().equals(uuid)){
                        dto.setEmail(authDto.getEmail());
                        dto.setRole(authDto.getRole());
                    }
                }
            }

            return responseDtoList;

        } catch (UserNotFoundException e){ return null; }
    }
}
