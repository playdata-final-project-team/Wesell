package com.wesell.adminservice.service;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.AdminUserResponseDto;
import com.wesell.adminservice.dto.response.PostListResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import com.wesell.adminservice.error.ErrorCode;
import com.wesell.adminservice.error.InternalServerException;
import com.wesell.adminservice.feignClient.AuthFeignClient;
import com.wesell.adminservice.feignClient.DealFeignClient;
import com.wesell.adminservice.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserFeignClient userFeignClient;
    private final AuthFeignClient authFeignClient;
    private final DealFeignClient dealFeignClient;
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public Page<UserListResponseDto> getUserList(int page, int size) {
        try {
            return userFeignClient.getUserList(page, size);
        } catch (Exception e) {
            logger.error("유저 전체 목록 조회 오류 발생", e);
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR_GET_USER_LIST);
        }
    }

    public void changeUserRole(ChangeRoleRequestDto requestDto) {
        try {
            authFeignClient.changeUserRole(requestDto);
        } catch (Exception e) {
            logger.error("회원 권한 변경 오류 발생", e);
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR_CHANGE_USER_ROLE);
        }
    }

    public Page<PostListResponseDto> getPostList(String uuid, int page, int size) {
        try {
            return dealFeignClient.getPostList(uuid, page, size);
        } catch (Exception e) {
            logger.error("글 목록 조회 오류 발생", e);
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR_GET_POST_LIST);
        }
    }

    public String userIsForced(String uuid) {
        try {
            return authFeignClient.updateIsForced(uuid).getBody();
        } catch (Exception e) {
            logger.error("회원 강제 탈퇴 오류 발생", e);
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR_USER_ISFORCED);
        }
    }

    public void postIsDeleted(String uuid, Long postId) {
        try {
            dealFeignClient.deletePost(uuid, postId);
        } catch (Exception e) {
            logger.error("글 강제 삭제 오류 발생", e);
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR_POST_ISDELETED);
        }
    }

    public Page<AdminUserResponseDto> searchUsers(String name, String nickname, String phone, String uuid, int page, int size) {
        try {
            return userFeignClient.searchUsers(name, nickname, phone, uuid, page, size);
        } catch (Exception e) {
            logger.error("회원 목록 검색 오류 발생", e);
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR_SEARCH_USERS);
        }
    }
}
