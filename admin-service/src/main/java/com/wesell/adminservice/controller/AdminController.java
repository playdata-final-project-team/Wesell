package com.wesell.adminservice.controller;

import com.wesell.adminservice.dto.request.AdminAuthIsForcedRequestDto;
import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.request.SiteConfigRequestDto;
import com.wesell.adminservice.dto.response.*;
import com.wesell.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public Page<UserListResponseDto> getUserList(@RequestParam("page") int page,
                                                 @RequestParam("size") int size) {
        return adminService.getUserList(page, size);
    }

    @GetMapping("version")
    public ResponseEntity<Map<String, String>> getVersionAndSave(
            @RequestParam(name = "jsVersion", defaultValue = "1.0") String jsVersion,
            @RequestParam(name = "cssVersion", defaultValue = "1.0") String cssVersion,
            @RequestParam(name = "title", defaultValue = "Default Title") String title) {

        Map<String, String> versions = new HashMap<>();
        versions.put("jsVersion", jsVersion);
        versions.put("cssVersion", cssVersion);
        versions.put("title", title);
        versionService.setVersions(versions);
        return new ResponseEntity<>(versions, HttpStatus.OK);
    }

    @PutMapping("/{uuid}/change-role")
    public ResponseEntity<String> changeUserRole(@PathVariable String uuid, @RequestBody ChangeRoleRequestDto changeRoleRequestDto) {
        try {
            adminService.changeUserRole(uuid, changeRoleRequestDto.getRole());
            return new ResponseEntity<>("User role changed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change user role: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get/post")
    public Page<PostListResponseDto> getPostList(@RequestParam("uuid") String uuid,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("size") int size){
        return adminService.getPostList(uuid, page, size);
    }

    @PatchMapping("updateIsForced")
    public ResponseEntity<AdminAuthIsForcedResponseDto> updateIsForced(@PathVariable String uuid) {
        return new ResponseEntity<>(adminService.updateIsForced(uuid), HttpStatus.OK);
    }
  
    @PutMapping("deletePost")
    public ResponseEntity<?> deletePost(@RequestParam("uuid") String uuid,
                                        @RequestParam("id") Long postId) {
        adminService.deletePost(uuid, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("search")
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
