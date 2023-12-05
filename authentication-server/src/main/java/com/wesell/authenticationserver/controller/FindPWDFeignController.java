package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.dto.request.FindPWDRequestDto;
import com.wesell.authenticationserver.exception.InvalidCodeException;
import com.wesell.authenticationserver.exception.UserNotFoundException;
import com.wesell.authenticationserver.global.util.SmsUtil;
import com.wesell.authenticationserver.service.userservice.FindPWDFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FindPWDFeignController {
    private final FindPWDFeignService findPWDFeignService;
    private final SmsUtil smsUtil;

    @GetMapping("/find/pwd/email")
    public ResponseEntity<?> findEmailByUuid(@RequestParam("email") String email) throws UserNotFoundException {
        String userId = findPWDFeignService.findUuid(email);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/send/pwd/phone")
    public ResponseEntity<?> sendPhoneForPWD(@RequestBody String code) {
        boolean isCodeValid = smsUtil.isCodeValid(code);
        if(isCodeValid) {
            return ResponseEntity.ok(HttpStatus.OK);
        }
        else {
            throw new InvalidCodeException("인증 정보가 일치하지 않습니다.");
        }
    }

    @PostMapping("/update/pwd/{uuid}")
    public ResponseEntity<?> updatePWD(@PathVariable("uuid") String uuid, @RequestBody FindPWDRequestDto findPWDRequestDto) {
        findPWDFeignService.setPassword(uuid, findPWDRequestDto.getPwd(), findPWDRequestDto.getRePwd());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
