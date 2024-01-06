package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthFeignController {

    private final AuthServiceImpl authService;

    // 회원 목록 조회
    @GetMapping("feign/auth-list")
    public List<?> authUserListFeignToUser(){
        return authService.getAllForFeign();
    }

    // 회원 이메일 조회
    @GetMapping("{uuid}/email")
    public String getUserByEmail(@PathVariable("uuid") String uuid) {
        String email = authService.findEmail(uuid);
        return email;
    }

}
