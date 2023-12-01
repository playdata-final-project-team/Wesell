package com.wesell.adminservice.controller;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.AdminAuthIsForcedResponseDto;
import com.wesell.adminservice.dto.response.AdminUserResponseDto;
import com.wesell.adminservice.dto.response.PostListResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import com.wesell.adminservice.service.AdminService;
import com.wesell.adminservice.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            @RequestParam(name = "title", defaultValue = "Default Title") String title) {

        Map<String, String> versions = new HashMap<>();
        versions.put("jsVersion", jsVersion);
        versions.put("cssVersion", cssVersion);
        versions.put("title", title);
        versionService.setVersions(versions);
        return new ResponseEntity<>(versions, HttpStatus.OK);
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
    public ResponseEntity<AdminAuthIsForcedResponseDto> updateIsForced(@PathVariable String uuid) {
        return new ResponseEntity<>(adminService.updateIsForced(uuid), HttpStatus.OK);
    }

    @PutMapping("deletePost")
    public ResponseEntity<?> deletePost(@RequestParam("uuid") String uuid, @RequestParam("id") Long postId) {
        adminService.deletePost(uuid, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("search")
    public List<AdminUserResponseDto> searchUsers(@RequestParam("name") Optional<String> name,
                                                  @RequestParam("nickname") Optional<String> nickname,
                                                  @RequestParam("phone") Optional<String> phone,
                                                  @RequestParam("uuid") Optional<String> uuid) {
        return adminService.searchUsers(name, nickname, phone, uuid);
    }
}
