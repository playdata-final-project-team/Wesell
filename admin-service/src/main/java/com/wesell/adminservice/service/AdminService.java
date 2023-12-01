package com.wesell.adminservice.service;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.AdminAuthIsForcedResponseDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public AdminAuthIsForcedResponseDto updateIsForced(String uuid) {
        return authFeignClient.updateIsForced(uuid).getBody();
    }

    public void deletePost(String uuid, Long postId) {
        dealFeignClient.deletePost(uuid, postId);
    }

    public List<AdminUserResponseDto> searchUsers(Optional<String> name,
                                                  Optional<String> nickname,
                                                  Optional<String> phone,
                                                  Optional<String> uuid) {

        // 각 Optional 파라미터가 비어있지 않으면 해당 값을 사용하고, 비어있으면 무시
        String searchName = name.orElse(null);
        String searchNickname = nickname.orElse(null);
        String searchPhone = phone.orElse(null);
        String searchUuid = uuid.orElse(null);

        return userFeignClient.searchUsers(searchName, searchNickname, searchPhone, searchUuid);
    }
}