package com.wesell.userservice.controller;

import com.wesell.userservice.dto.feigndto.AdminFeignResponseDto;
import com.wesell.userservice.dto.response.MypageResponseDto;
import com.wesell.userservice.exception.UserNotFoundException;
import com.wesell.userservice.service.AdminFeignService;
import com.wesell.userservice.service.UserService;
import com.wesell.userservice.service.feign.AuthServerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-service")
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

    @PostMapping("feign/find/id")
    public ResponseEntity<String> findID(@RequestBody String phoneNumber) throws UserNotFoundException {
        String userId = userService.findIDPWD(phoneNumber);

        return ResponseEntity.ok(userId);
    }
}
