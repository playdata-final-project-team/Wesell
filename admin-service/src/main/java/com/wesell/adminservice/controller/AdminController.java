package com.wesell.adminservice.controller;

import com.wesell.adminservice.domain.dto.request.SiteConfigRequestDto;
import com.wesell.adminservice.domain.dto.response.SiteConfigResponseDto;
import com.wesell.adminservice.domain.dto.response.UserListResponseDto;
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

    @PostMapping("set/config")
    public ResponseEntity<SiteConfigResponseDto> saveSiteConfig(@RequestBody SiteConfigRequestDto siteConfigRequestDto) {
        SiteConfigResponseDto savedSiteConfig = adminService.saveSiteConfig(siteConfigRequestDto);
        return new ResponseEntity<>(savedSiteConfig, HttpStatus.CREATED);
    }

    @GetMapping("get/config")
    public ResponseEntity<SiteConfigResponseDto> getSiteConfig() {
        SiteConfigResponseDto currentSiteConfig = adminService.getSiteConfig();
        return new ResponseEntity<>(currentSiteConfig, HttpStatus.OK);
    }

    @GetMapping("get/users")
    public ResponseEntity<List<UserListResponseDto>> getUserList() {
        return adminService.getUserList();
    }

    @GetMapping("version")
    public ResponseEntity<SiteConfigResponseDto> getVersionAndSave(
            @RequestParam(name = "jsVersion", defaultValue = "1.0") String jsVersion,
            @RequestParam(name = "cssVersion", defaultValue = "1.0") String cssVersion,
            @RequestParam(name = "title", defaultValue = "Default Title") String title) {

        Map<String, String> versions = new HashMap<>();
        versions.put("jsVersion", jsVersion);
        versions.put("cssVersion", cssVersion);
        versions.put("title", title);
        SiteConfigRequestDto requestDto = adminService.mapToRequestAdminDto(versions);
        SiteConfigResponseDto savedSiteConfig = adminService.saveSiteConfig(requestDto);
        return new ResponseEntity<>(savedSiteConfig, HttpStatus.CREATED);
    }
}
