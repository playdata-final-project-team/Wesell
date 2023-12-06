package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.dto.request.AdminAuthRoleRequestDto;
import com.wesell.authenticationserver.service.AuthUserService;
import com.wesell.authenticationserver.service.dto.response.AdminAuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin-auth-server")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AuthUserService authUserService;

    @PutMapping("change-role")
    public ResponseEntity<AdminAuthResponseDto> changeUserRole(@RequestBody AdminAuthRoleRequestDto requestDto) {
        return new ResponseEntity<>(authUserService.updateRole(requestDto.getUuid(), requestDto.getRole()), HttpStatus.OK);
    }

    @PutMapping("updateIsForced/{uuid}")
    public ResponseEntity<AdminAuthResponseDto> updateIsForced(@PathVariable String uuid) {
        return new ResponseEntity<>(authUserService.updateIsForced(uuid), HttpStatus.OK);
    }
    @PutMapping("updateIsDeleted/{uuid}")
    public ResponseEntity<AdminAuthResponseDto> updateIsDeleted(@PathVariable String uuid) {
        return new ResponseEntity<>(authUserService.updateIsDeleted(uuid), HttpStatus.OK);
    }
}

