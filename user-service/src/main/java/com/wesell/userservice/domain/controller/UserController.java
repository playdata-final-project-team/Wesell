package com.wesell.userservice.domain.controller;


import com.wesell.userservice.domain.dto.UserDTO;
import com.wesell.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/signup")
    public String saveForm(){
        return "signup";
    }

    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
        try {
            userService.save(userDTO);
            return new ResponseEntity<>("Signup successful", HttpStatus.OK);
        } catch (Exception e) {
            // 회원가입 실패 시 예외 처리
            return new ResponseEntity<>("Signup failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
