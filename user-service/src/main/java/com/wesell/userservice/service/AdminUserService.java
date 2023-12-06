package com.wesell.userservice.service;

import com.wesell.userservice.domain.entity.User;
import com.wesell.userservice.domain.repository.AdminUserRepository;
import com.wesell.userservice.dto.feigndto.AdminFeignResponseDto;
import com.wesell.userservice.dto.feigndto.AuthUserInfoRequestDto;
import com.wesell.userservice.dto.response.AdminUserResponseDto;
import com.wesell.userservice.service.feign.AuthServerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AuthServerFeignClient authServerFeignClient;

    public Page<AdminUserResponseDto> searchUsers(String name,
                                                        String nickname,
                                                        String phone,
                                                        String uuid,
                                                        int page,
                                                        int size){
        Pageable pageable = PageRequest.of(page, size);

        // 페이징된 검색 결과를 가져옴
        Page<User> pagedUsers = adminUserRepository.findByNameContainingOrNicknameContainingOrPhoneContainingOrUuidContaining(
                name, nickname, phone, uuid, pageable
        );

        // 페이징된 결과를 AdminUserResponseDto로 변환하여 반환
        return pagedUsers.map(user -> AdminUserResponseDto.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .uuid(user.getUuid())
                .build()
        );
    }

    public Page<AdminFeignResponseDto> getUserList(int page, int size) {
        // 사용자 정보를 페이징해서 가져옴
        Page<User> userInfoPage = adminUserRepository.findAll(PageRequest.of(page, size));

        List<AuthUserInfoRequestDto> authUserInfoList = authServerFeignClient.getListAuthUserInfo();

        // 페이징된 응답 DTO 리스트 생성
        List<AdminFeignResponseDto> responseDtoList = userInfoPage.getContent().stream().map(user ->
                AdminFeignResponseDto.builder()
                        .uuid(user.getUuid())
                        .name(user.getName())
                        .phone(user.getPhone())
                        .nickname(user.getNickname())
                        .build()
        ).toList();

        // AuthUserInfo 리스트에서 매칭되는 정보 찾아 업데이트
        for (AdminFeignResponseDto dto : responseDtoList) {
            String uuid = dto.getUuid();

            for (AuthUserInfoRequestDto authDto : authUserInfoList) {
                if (authDto.getUuid().equals(uuid)) {
                    dto.setEmail(authDto.getEmail());
                    dto.setRole(authDto.getRole());
                }
            }
        }

        // 페이징된 데이터를 포함하는 Page 객체로 변환하여 반환
        return new PageImpl<>(responseDtoList, userInfoPage.getPageable(), userInfoPage.getTotalElements());
    }
}
