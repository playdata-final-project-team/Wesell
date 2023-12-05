package com.wesell.adminservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.wesell.adminservice.dto.request.AdminAuthIsForcedRequestDto;
import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.*;
import com.wesell.adminservice.dto.request.SiteConfigRequestDto;
import com.wesell.adminservice.domain.enum_.Role;
import com.wesell.adminservice.feignClient.AuthFeignClient;
import com.wesell.adminservice.feignClient.DealFeignClient;
import com.wesell.adminservice.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserFeignClient userFeignClient;
    private final AuthFeignClient authFeignClient;
    private final DealFeignClient dealFeignClient;

    public Page<UserListResponseDto> getUserList(int page, int size){
        return userFeignClient.getUserList(page, size);
    }

        public SiteConfigRequestDto mapToRequestAdminDto(Map<String, String> versions) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(versions, SiteConfigRequestDto.class);
    }

    public void changeUserRole(String uuid, Role role) {
        ChangeRoleRequestDto requestDto = new ChangeRoleRequestDto();
        requestDto.setRole(role);
        ResponseEntity<String> response = authFeignClient.changeUserRole(uuid, requestDto);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("User role changed successfully");
        } else {
            System.out.println("Failed to change user role: " + response.getBody());
        }
    }

    public Page<PostListResponseDto> getPostList(String uuid,
                                                 int page,
                                                 int size) {
        return dealFeignClient.getPostList(uuid, page, size);
    }

    public AdminAuthIsForcedResponseDto updateIsForced(AdminAuthIsForcedRequestDto requestDto) {
        return authFeignClient.updateIsForced(requestDto).getBody();
    }

    public void deletePost(String uuid, Long postId) {
        dealFeignClient.deletePost(uuid, postId);
    }

    public Page<AdminUserResponseDto> searchUsers(String name,
                                                  String nickname,
                                                  String phone,
                                                  String uuid,
                                                  int page,
                                                  int size) {

        return userFeignClient.searchUsers(name, nickname, phone, uuid, page, size);
    }
}