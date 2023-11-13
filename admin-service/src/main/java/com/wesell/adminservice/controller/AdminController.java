package com.wesell.adminservice.controller;

import com.wesell.adminservice.domain.dto.RequestAdminDto;
import com.wesell.adminservice.domain.dto.ResponseAdminDto;
import com.wesell.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("set-config")
    public ResponseEntity<ResponseAdminDto> saveSiteConfig(@RequestBody RequestAdminDto requestAdminDto) {
        ResponseAdminDto savedSiteConfig = adminService.saveSiteConfig(requestAdminDto);
        return new ResponseEntity<>(savedSiteConfig, HttpStatus.CREATED);
    }

    @GetMapping("get-config")
    public ResponseEntity<ResponseAdminDto> getSiteConfig() {
        ResponseAdminDto currentSiteConfig = adminService.getSiteConfig();
        return new ResponseEntity<>(currentSiteConfig, HttpStatus.OK);
    }

    @GetMapping("version")
    public ResponseEntity<ResponseAdminDto> getVersionAndSave(
            @RequestParam(name = "jsVersion", defaultValue = "1.0") String jsVersion,
            @RequestParam(name = "cssVersion", defaultValue = "1.0") String cssVersion,
            @RequestParam(name = "title", defaultValue = "Default Title") String title) {

        Map<String, String> versions = new HashMap<>();
        versions.put("jsVersion", jsVersion);
        versions.put("cssVersion", cssVersion);
        versions.put("title", title);
        RequestAdminDto requestAdminDto = adminService.mapToRequestAdminDto(versions);
        ResponseAdminDto savedSiteConfig = adminService.saveSiteConfig(requestAdminDto);
        return new ResponseEntity<>(savedSiteConfig, HttpStatus.CREATED);
    }
}
