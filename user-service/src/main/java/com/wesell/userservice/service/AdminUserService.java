package com.wesell.userservice.service;

import com.wesell.userservice.domain.entity.User;
import com.wesell.userservice.domain.repository.AdminUserRepository;
import com.wesell.userservice.dto.request.AdminUserRequestDto;
import com.wesell.userservice.dto.response.AdminUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    public List<AdminUserResponseDto> searchUsers(String name,
                                                  String nickname,
                                                  String phone,
                                                  String uuid){

        // 검색 결과를 UserSearchResponseDTO로 변환
        List<AdminUserResponseDto> responseDtos = convertToResponseDtos(
                adminUserRepository.findByNameContaining(name),
                adminUserRepository.findByNicknameContaining(nickname),
                adminUserRepository.findByPhoneContaining(phone),
                adminUserRepository.findByUuidContaining(uuid)
        );

        return responseDtos;
    }

    private List<AdminUserResponseDto> convertToResponseDtos(
            List<User> usersByName,
            List<User> usersByNickname,
            List<User> usersByPhone,
            List<User> usersByUuid) {

        // 검색 결과 리스트의 크기를 가져옴
        int minSize = Math.min(Math.min(usersByName.size(), usersByNickname.size()), Math.min(usersByPhone.size(), usersByUuid.size()));

        // 검색 결과를 변환하여 리스트로 반환
        return IntStream.range(0, minSize)
                .mapToObj(i -> AdminUserResponseDto.builder()
                        .name(usersByName.get(i).getName())
                        .nickname(usersByNickname.get(i).getNickname())
                        .phone(usersByPhone.get(i).getPhone())
                        .uuid(usersByUuid.get(i).getUuid())
                        .build())
                .collect(Collectors.toList());
    }
}
