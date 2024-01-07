package com.wesell.adminservice.domain.service;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.AdminUserResponseDto;
import com.wesell.adminservice.dto.response.PostListResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import org.springframework.data.domain.Page;

public interface AdminService {
    // 회원관리 - 회원 목록
    Page<UserListResponseDto> getUserList(int page, int size);
    // 회원관리 - 회원 검색 목록 조회
    Page<AdminUserResponseDto> searchUsers(String name, String nickname, String phone, String uuid, int page, int size);
    // 회원관리 - 회원 권한 변경
    void changeUserRole(ChangeRoleRequestDto requestDto);
    // 회원관리 - 회원 강제 탈퇴
    String forcedDelete(String uuid);
    // 판매글 관리 - 판매글 목록
    Page<PostListResponseDto> getPostList(String uuid, int page, int size);
    // 판매글 관리 - 판매글 삭제
    void deletePost(String uuid, Long postId);
}
