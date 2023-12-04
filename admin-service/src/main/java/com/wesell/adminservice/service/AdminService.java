package com.wesell.adminservice.service;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.AdminUserResponseDto;
import com.wesell.adminservice.dto.response.PostListResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import com.wesell.adminservice.feignClient.AuthFeignClient;
import com.wesell.adminservice.feignClient.DealFeignClient;
import com.wesell.adminservice.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
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

    public ResponseEntity<List<UserListResponseDto>> getUserList(){
        return userFeignClient.getUserList();
    }

    public void changeUserRole(ChangeRoleRequestDto requestDto) {
        ResponseEntity<String> response = authFeignClient.changeUserRole(requestDto);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("User role changed successfully");
        } else {
            System.out.println("Failed to change user role: " + response.getBody());
        }
    }

    public ResponseEntity<List<PostListResponseDto>> getPostList(String uuid) {
        return dealFeignClient.getPostList(uuid);
    }

    public String updateIsForced(String uuid) {
        return authFeignClient.updateIsForced(uuid).getBody();
    }

    public void deletePost(String uuid, Long postId) {
        dealFeignClient.deletePost(uuid, postId);
    }

    public List<AdminUserResponseDto> searchUsers(String name,
                                                  String nickname,
                                                  String phone,
                                                  String uuid) {

        return userFeignClient.searchUsers(name, nickname, phone, uuid);
    }
}