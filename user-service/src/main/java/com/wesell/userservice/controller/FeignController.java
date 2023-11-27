package com.wesell.userservice.controller;

import com.wesell.userservice.dto.feigndto.AdminFeignResponseDto;
import com.wesell.userservice.dto.response.MypageResponseDto;
import com.wesell.userservice.service.AdminFeignService;
import com.wesell.userservice.service.UserService;
import com.wesell.userservice.service.feign.AuthServerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeignController {

    private final AuthServerFeignClient authServerFeignClient;
    private final AdminFeignService adminFeignService;
    private final UserService userService;

    @GetMapping("feign/user-list")
    public List<AdminFeignResponseDto> allUserListFeignToAdmin(){
        return adminFeignService.getAllForFeign();
    }

    @GetMapping("feign/mypage/{uuid}")
    public MypageResponseDto getMyPageDetails(@PathVariable String uuid){
        return userService.getMyPageDetails(uuid);
    }
}
