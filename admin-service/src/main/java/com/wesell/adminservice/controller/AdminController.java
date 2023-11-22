package com.wesell.adminservice.controller;

import com.wesell.adminservice.dto.request.AdminAuthIsForcedRequestDto;
import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.request.SiteConfigRequestDto;
import com.wesell.adminservice.dto.response.AdminAuthIsForcedResponseDto;
import com.wesell.adminservice.dto.response.PostListResponseDto;
import com.wesell.adminservice.dto.response.SiteConfigResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import com.wesell.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("get-version")
    public ResponseEntity<Map<String, String>> getVersions() {
        Map<String, String> currentVersions = adminService.getVersions();
        return new ResponseEntity<>(currentVersions, HttpStatus.OK);
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
        adminService.setVersions(versions);
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
    public ResponseEntity<List<PostListResponseDto>> getPostList(@RequestParam("uuid") String uuid){
        return adminService.getPostList(uuid);
    }

    @PatchMapping("updateIsForced")
    public ResponseEntity<AdminAuthIsForcedResponseDto> updateIsForced(@RequestBody AdminAuthIsForcedRequestDto requestDto) {
        AdminAuthIsForcedResponseDto responseDto = adminService.updateIsForced(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
  
    @PutMapping("deletePost")
    public ResponseEntity<?> deletePost(@RequestParam("uuid") String uuid, @RequestParam("id") Long postId) {
        adminService.deletePost(uuid, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
