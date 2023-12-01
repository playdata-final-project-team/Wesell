package com.wesell.adminservice.controller;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.AdminUserResponseDto;
import com.wesell.adminservice.dto.response.PostListResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import com.wesell.adminservice.service.AdminService;
import com.wesell.adminservice.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final VersionService versionService;

    @GetMapping("get-version")
    public ResponseEntity<Map<String, String>> getVersions() {
        return new ResponseEntity<>(versionService.getVersions(), HttpStatus.OK);
    }

    @GetMapping("get/users")
    public ResponseEntity<List<UserListResponseDto>> getUserList() {
        return adminService.getUserList();
    }

    @GetMapping("version")
    public ResponseEntity<Map<String, String>> getVersionAndSave(
            @RequestParam(name = "jsVersion", defaultValue = "1.0") String jsVersion,
            @RequestParam(name = "cssVersion", defaultValue = "1.0") String cssVersion,
            @RequestParam(name = "title", defaultValue = "Default Title") String title,
            @RequestParam(name = "agree", defaultValue = "개인정보 제공에 동의하십니까?") String agree) {
        return new ResponseEntity<>(versionService.setVersions(jsVersion, cssVersion, title, agree), HttpStatus.OK);
    }

    @PutMapping("change-role")
    public ResponseEntity<String> changeUserRole(@RequestBody ChangeRoleRequestDto requestDto) {
        try {
            adminService.changeUserRole(requestDto);
            return new ResponseEntity<>("User role changed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change user role: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get/post")
    public ResponseEntity<List<PostListResponseDto>> getPostList(@RequestParam("uuid") String uuid){
        return adminService.getPostList(uuid);
    }

    @PutMapping("updateIsForced/{uuid}")
    public ResponseEntity<String> updateIsForced(@PathVariable String uuid) {
        return new ResponseEntity<>(adminService.updateIsForced(uuid), HttpStatus.OK);
    }

    @PutMapping("deletePost")
    public ResponseEntity<?> deletePost(@RequestParam("uuid") String uuid, @RequestParam("id") Long postId) {
        adminService.deletePost(uuid, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("search")
    public List<AdminUserResponseDto> searchUsers(@RequestParam(name = "name", defaultValue = "") String name,
                                                  @RequestParam(name = "nickname", defaultValue = "") String nickname,
                                                  @RequestParam(name = "phone", defaultValue = "") String phone,
                                                  @RequestParam(name = "uuid", defaultValue = "") String uuid) {
        return adminService.searchUsers(name, nickname, phone, uuid);
    }
}
