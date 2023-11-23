package com.wesell.userservice.controller;

import com.wesell.userservice.dto.feigndto.AdminFeignResponseDto;
import com.wesell.userservice.service.AdminFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeignController {

    private final AdminFeignService adminFeignService;

    @GetMapping("feign/user-list")
    public List<AdminFeignResponseDto> allUserListFeignToAdmin(){
        return adminFeignService.getAllForFeign();
    }
}
