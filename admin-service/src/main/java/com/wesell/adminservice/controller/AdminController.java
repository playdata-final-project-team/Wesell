package com.wesell.adminservice.controller;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.AdminUserResponseDto;
import com.wesell.adminservice.dto.response.PostListResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import com.wesell.adminservice.service.AdminService;
import com.wesell.adminservice.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AdminController {

    private final AdminService adminService;
    private final VersionService versionService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "서버 연결 가능한 상태입니다 15트";
    }
    @GetMapping("get-version") //버전 값 불러오기//기본값도 들어가있다
    public ResponseEntity<Map<String, String>> getVersions() {
        return new ResponseEntity<>(versionService.getVersions(), HttpStatus.OK);
    }

    @GetMapping("get/users")//유저리스트
    public Page<UserListResponseDto> getUserList(@RequestParam("page") int page,
                                                 @RequestParam("size") int size) {
        return adminService.getUserList(page, size);
    }

    @GetMapping("version") //버전저장
    public ResponseEntity<Map<String, String>> getVersionAndSave(
            @RequestParam(name = "jsVersion", defaultValue = "1.0") String jsVersion,
            @RequestParam(name = "cssVersion", defaultValue = "1.0") String cssVersion,
            @RequestParam(name = "title", defaultValue = "Default Title") String title,
            @RequestParam(name = "agree", defaultValue = "개인정보 제공에 동의하십니까?") String agree) {
        return new ResponseEntity<>(versionService.setVersions(jsVersion, cssVersion, title, agree), HttpStatus.OK);
    }

    @PutMapping("change-role") //유저권한수정
    public ResponseEntity<String> changeUserRole(@RequestBody ChangeRoleRequestDto requestDto) {
        adminService.changeUserRole(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get/post")
    public Page<PostListResponseDto> getPostList(@RequestParam("uuid") String uuid,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("size") int size){
        return adminService.getPostList(uuid, page, size);
    }

    @PutMapping("updateIsForced/{uuid}") //회원강제탈퇴
    public ResponseEntity<String> updateIsForced(@PathVariable String uuid) {
        return new ResponseEntity<>(adminService.userIsForced(uuid), HttpStatus.OK);
    }

    @PutMapping("deletePost")
    public ResponseEntity<?> deletePost(@RequestParam("uuid") String uuid,
                                        @RequestParam("id") Long postId) {
        adminService.postIsDeleted(uuid, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("search") //유저검색
    public Page<AdminUserResponseDto> searchUsers(@RequestParam(name = "name", defaultValue = "") String name,
                                                  @RequestParam(name = "nickname", defaultValue = "") String nickname,
                                                  @RequestParam(name = "phone", defaultValue = "") String phone,
                                                  @RequestParam(name = "uuid", defaultValue = "") String uuid,
                                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "10") int size)
    {
        return adminService.searchUsers(name, nickname, phone, uuid, page, size);
    }
}
