package com.wesell.userservice.controller;

import com.wesell.userservice.dto.response.FindIDResponseDto;
import com.wesell.userservice.exception.UserNotFoundException;
import com.wesell.userservice.global.util.SmsUtil;
import com.wesell.userservice.service.FindIDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-service")
@RequiredArgsConstructor
public class FindIDController {

    private final FindIDService findIDService;
    private final SmsUtil smsUtil;

    @GetMapping("phone/verify")
    public ResponseEntity<?> verifyCode(@RequestParam String phoneNumber, @RequestParam String code) throws UserNotFoundException {
        boolean isCodeValid = smsUtil.isCodeValid(code);
        if(isCodeValid) {
            FindIDResponseDto userId = findIDService.findID(phoneNumber);
            return ResponseEntity.ok(userId);
        }
        else {
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않습니다.");
        }
    }
}
