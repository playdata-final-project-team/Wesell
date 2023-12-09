package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.response.SuccessCode;
import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.service.userservice.UserDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UserDeleteController {
    private final UserDeleteService userDeleteService;
    private final CustomCookie cookieUtil;

    @GetMapping("delete")
    public ResponseEntity<?> deletePage() {
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("delete/user/{uuid}")
    public ResponseEntity<?> deleteByUser(@PathVariable("uuid") String uuid, @RequestBody String pwd) {
        userDeleteService.deleteByUser(uuid, pwd);
        ResponseCookie deleteAccessToken = cookieUtil.deleteTokenCookie();

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .body(null);
    }
}
