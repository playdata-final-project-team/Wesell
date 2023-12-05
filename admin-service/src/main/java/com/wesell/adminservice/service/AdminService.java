package com.wesell.adminservice.service;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.AdminUserResponseDto;
import com.wesell.adminservice.dto.response.PostListResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import com.wesell.adminservice.feignClient.AuthFeignClient;
import com.wesell.adminservice.feignClient.DealFeignClient;
import com.wesell.adminservice.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserFeignClient userFeignClient;
    private final AuthFeignClient authFeignClient;
    private final DealFeignClient dealFeignClient;

    public Page<UserListResponseDto> getUserList(int page, int size){
        return userFeignClient.getUserList(page, size);
    }

    public void changeUserRole(ChangeRoleRequestDto requestDto) {
        ResponseEntity<String> response = authFeignClient.changeUserRole(requestDto);

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

    public String updateIsForced(String uuid) {
        return authFeignClient.updateIsForced(uuid).getBody();
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