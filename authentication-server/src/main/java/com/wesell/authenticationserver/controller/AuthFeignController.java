package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.dto.request.ChangeRoleRequestDto;
import com.wesell.authenticationserver.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthFeignController {

    private final AuthServiceImpl authService;

    // 회원 - 관리자 회원관리 - 회원 목록 조회
    @GetMapping("feign/auth-list")
    public List<?> authUserListFeignToUser(){
        return authService.getAllForFeign();
    }

    // 회원 - 마이페이지 - 회원 이메일 조회
    @GetMapping("{uuid}/email")
    public String getUserByEmail(@PathVariable("uuid") String uuid) {
        String email = authService.findEmail(uuid);
        return email;
    }

    // 관리자 - 회원관리 - 권한 변경
    @PutMapping("change-role")
    public void changeUserRole(@RequestBody ChangeRoleRequestDto requestDto) {
        authService.updateRole(requestDto);
    }

    // 관리자 - 회원관리 - 강제 탈퇴
    @PutMapping("updateIsForced/{uuid}")
    public void updateIsForced(@PathVariable String uuid) {
        authService.updateIsForced(uuid);
    }
}
