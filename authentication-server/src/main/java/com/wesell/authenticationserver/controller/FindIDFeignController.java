package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.dto.request.FindIDRequestDto;
import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import com.wesell.authenticationserver.exception.InvalidCodeException;
import com.wesell.authenticationserver.exception.UserNotFoundException;
import com.wesell.authenticationserver.global.util.SmsUtil;
import com.wesell.authenticationserver.service.feign.UserServiceFeignClient;
import com.wesell.authenticationserver.service.userservice.FindIDFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class FindIDFeignController {

    private final FindIDFeignService findIDFeignService;
    private final UserServiceFeignClient findIDFeignClient;

    @PostMapping("/send/id/phone")
    public ResponseEntity<?> sendPhoneForID(@RequestBody FindIDRequestDto findIDRequestDto) {
        try {
            String userId = findIDFeignClient.findID(findIDRequestDto.getPhoneNumber());
            return ResponseEntity.ok(userId);
        }catch(Exception e){
            throw new CustomException(ErrorCode.USER_SERVICE_FEIGN_ERROR);
        }
    }

    @GetMapping("/find/email/{uuid}")
    public ResponseEntity<?> findID(@PathVariable("uuid") String uuid) throws UserNotFoundException {
        String email = findIDFeignService.findId(uuid);
        return ResponseEntity.ok(email);
    }
}
