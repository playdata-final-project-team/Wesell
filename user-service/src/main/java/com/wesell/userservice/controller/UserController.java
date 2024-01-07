package com.wesell.userservice.controller;

import com.wesell.userservice.controller.response.NewResponseDto;
import com.wesell.userservice.dto.feigndto.SignUpResponseDto;
import com.wesell.userservice.dto.request.SignupRequestDto;
import com.wesell.userservice.dto.response.MypageResponseDto;
import com.wesell.userservice.dto.response.ResponseDto;
import com.wesell.userservice.error.exception.SuccessCode;
import com.wesell.userservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UserController {

    private final UserServiceImpl userService;

    // 마이페이지 - 회원 정보 조회
    @GetMapping("users/{uuid}")
    public ResponseEntity<?> findUserResponseEntity(@PathVariable("uuid") String uuid) {
        MypageResponseDto responseDto = userService.getMyInfo(uuid);
        return ResponseEntity.ok(responseDto);
    }

    // 회원 정보 수정
    @PutMapping("users/{uuid}")
    public ResponseEntity<?> updateUser(@PathVariable String uuid, @RequestBody SignupRequestDto signupRequestDTO) {
        userService.update(uuid, signupRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(NewResponseDto.of(SuccessCode.OK));
    }

    // 닉네임 중복 확인
    @GetMapping("dup-check")
    public ResponseEntity<NewResponseDto> checkNickname(@RequestParam("nickname") String nickname) {
        userService.checkNickname(nickname);
        return ResponseEntity.ok(NewResponseDto.of(SuccessCode.OK));
    }

    // 전체 회원 조회
    // todo : 필요??
    @GetMapping("users")
    public ResponseEntity<?> findUsersResponseEntity() {
        return ResponseEntity.ok(userService.findAll());

    }
}
