package com.wesell.userservice.controller;

import com.wesell.userservice.dto.request.AdminUserRequestDto;
import com.wesell.userservice.dto.response.AdminUserResponseDto;
import com.wesell.userservice.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService userService;

    @GetMapping("search-users")
    public List<AdminUserResponseDto> searchUsers(@RequestParam("name") String name,
                                                  @RequestParam("nickname") String nickname,
                                                  @RequestParam("phone") String phone,
                                                  @RequestParam("uuid") String uuid) {
        return userService.searchUsers(name, nickname, phone, uuid);
    }
}
