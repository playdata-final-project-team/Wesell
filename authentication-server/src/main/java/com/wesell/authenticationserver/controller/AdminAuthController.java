package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.dto.request.AdminAuthIsForcedRequestDto;
import com.wesell.authenticationserver.controller.dto.request.AdminAuthRoleRequestDto;
import com.wesell.authenticationserver.service.AuthUserService;
import com.wesell.authenticationserver.service.dto.response.AdminAuthIsForcedResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin-auth-server")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AuthUserService authUserService;

    @PutMapping("change-role/{uuid}")
    public ResponseEntity<String> changeUserRole(@PathVariable String uuid, @RequestBody AdminAuthRoleRequestDto adminAuthRoleRequestDto) {
        try {
            authUserService.updateRole(uuid, adminAuthRoleRequestDto.getRole());
            return new ResponseEntity<>("User role changed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change user role: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("updateIsForced")
    public ResponseEntity<AdminAuthIsForcedResponseDto> updateIsForced(@RequestBody AdminAuthIsForcedRequestDto requestDto) {
        AdminAuthIsForcedResponseDto responseDto = authUserService.updateIsForced(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

