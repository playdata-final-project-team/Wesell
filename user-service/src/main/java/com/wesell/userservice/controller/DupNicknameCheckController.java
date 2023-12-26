package com.wesell.userservice.controller;

import com.wesell.userservice.controller.response.NewResponseDto;
import com.wesell.userservice.error.exception.SuccessCode;
import com.wesell.userservice.service.DupNicknameCheckService;
import com.wesell.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Log4j2
public class DupNicknameCheckController {

    private final DupNicknameCheckService service;

    @GetMapping("/dup-check")
    public ResponseEntity<NewResponseDto> checkNickname(@RequestParam("nickname") String nickname){
        service.checkNickname(nickname);
        return ResponseEntity.ok(NewResponseDto.of(SuccessCode.OK));
    }
}
