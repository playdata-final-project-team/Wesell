package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.service.AuthUserService;
import com.wesell.authenticationserver.service.dto.feign.AuthUserListFeignResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthFeignController {

    private final AuthUserService authUserService;

    @GetMapping("feign/auth-list")
    public List<AuthUserListFeignResponseDto> authUserListFeignToUser(){
        return authUserService.getAllForFeign();
    }

}
