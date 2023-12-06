package com.wesell.userservice.controller;

import com.wesell.userservice.dto.feigndto.AdminFeignResponseDto;
import com.wesell.userservice.dto.response.AdminUserResponseDto;
import com.wesell.userservice.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("search-users")
    public Page<AdminUserResponseDto> searchUsers(@RequestParam("name") String name,
                                                  @RequestParam("nickname") String nickname,
                                                  @RequestParam("phone") String phone,
                                                  @RequestParam("uuid") String uuid,
                                                  @RequestParam("page") int page,
                                                  @RequestParam("size") int size){
        return adminUserService.searchUsers(name, nickname, phone, uuid, page, size);
    }

    @GetMapping("user-list")
    public Page<AdminFeignResponseDto> getUserList(@RequestParam("page") int page,
                                                               @RequestParam("size") int size){
        return adminUserService.getUserList(page, size);
    }
}
