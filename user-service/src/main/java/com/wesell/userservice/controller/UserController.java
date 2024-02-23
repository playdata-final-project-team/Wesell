package com.wesell.userservice.controller;

import com.wesell.userservice.dto.request.SignupRequestDto;
import com.wesell.userservice.dto.response.MypageResponseDto;
import com.wesell.userservice.global.response.success.SuccessApiResponse;
import com.wesell.userservice.global.response.success.SuccessCode;
import com.wesell.userservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UserController {

    private final UserServiceImpl userService;

    // 마이페이지 - 회원 정보 조회
    @GetMapping("users/{uuid}")
    public ResponseEntity<?> findUserResponseEntity(@PathVariable("uuid") String uuid) {
        MypageResponseDto responseDto = userService.getMyInfo(uuid);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK, responseDto));
    }

    // 회원 정보 수정
    @PutMapping("users/{uuid}")
    public ResponseEntity<?> updateUser(@PathVariable String uuid, @RequestBody SignupRequestDto signupRequestDTO) {
        userService.update(uuid, signupRequestDTO);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }

    // 닉네임 중복 확인
    @GetMapping("dup-check")
    public ResponseEntity<?> checkNickname(@RequestParam("nickname") String nickname) {
        userService.checkNickname(nickname);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }

    //판매 횟수 증가
    @PutMapping("dealCount")
    public ResponseEntity<?> updateDealCount(@RequestParam("id") String uuid) {
        return ResponseEntity.ok(userService.updateDealCount(uuid));
    }

    // 닉네임 조회
    @GetMapping("{uuid}/nickname")
    public ResponseEntity getNickname(@PathVariable String uuid){
        return ResponseEntity.ok(userService.getNicknameByUuid(uuid));
    }

}
