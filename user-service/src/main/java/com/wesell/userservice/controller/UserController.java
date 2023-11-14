package com.wesell.userservice.controller;

import com.wesell.userservice.dto.RequestSignupDTO;
import com.wesell.userservice.dto.responseDto;
import com.wesell.userservice.exception.UserNotFoundException;
import com.wesell.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("user")
    public ResponseEntity<responseDto> findUserResponseEntity(@RequestParam("uuid") String uuid) {

        try {
            return ResponseEntity.ok(userService.findUser(uuid));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("users")
    public ResponseEntity<List<responseDto>> findUsersResponseEntity() {

        try {
            return ResponseEntity.ok(userService.findUsers());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/api/signup")
    public ResponseEntity<String> signup(@RequestBody RequestSignupDTO requestSignupDTO) {
        try {
            userService.save(requestSignupDTO);
            return new ResponseEntity<>("Signup successful", HttpStatus.OK);
        } catch (Exception e) {
            // 회원가입 실패 시 예외 처리
            return new ResponseEntity<>("Signup failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
