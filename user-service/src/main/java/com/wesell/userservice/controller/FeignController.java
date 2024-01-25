package com.wesell.userservice.controller;

import com.wesell.userservice.dto.feigndto.AdminFeignResponseDto;
import com.wesell.userservice.dto.response.MypageResponseDto;
import com.wesell.userservice.error.exception.UserNotFoundException;
import com.wesell.userservice.service.AdminFeignService;
import com.wesell.userservice.service.UserService;
import com.wesell.userservice.service.feign.AuthServerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class FeignController {

    private final AuthServerFeignClient authServerFeignClient;
    private final AdminFeignService adminFeignService;
    private final UserService userService;

    @GetMapping("feign/user-list")
    public List<AdminFeignResponseDto> allUserListFeignToAdmin(){
        return adminFeignService.getAllForFeign();
    }

    @GetMapping("feign/mypage/{uuid}")
    public ResponseEntity<MypageResponseDto> getMyPageDetails(@PathVariable String uuid){
        MypageResponseDto responseDto = userService.getMyPageDetails(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("feign/find/id")
    public String findID(@RequestBody String phoneNumber) {
        return userService.findIDPWD(phoneNumber);
    }

    @PostMapping("FindNickName")
    public String findNicknameByUuid(@RequestBody String uuid) {

        return userService.getNicknameByUuid(uuid);

    }

    @PostMapping("getNickName")
    public String getNicknameByUuid(@RequestBody String uuid) {

        return userService.getNicknameByUuid(uuid);
    }
}
