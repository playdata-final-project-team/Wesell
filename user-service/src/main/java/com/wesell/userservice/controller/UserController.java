package com.wesell.userservice.controller;

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


    @DeleteMapping("user/{uuid}")
    public ResponseEntity<String> deleteUserEntity(@PathVariable String uuid) {
        try {
            userService.deleteUser(uuid);
            return ResponseEntity.ok("성공적으로 삭제되었습니다.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    @PostMapping("/api/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDTO) {
        try {
            userService.save(signupRequestDTO);
            return new ResponseEntity<>("Signup successful", HttpStatus.OK);
        } catch (Exception e) {
            // 회원가입 실패 시 예외 처리
            return new ResponseEntity<>("Signup failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }
    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateUser(@PathVariable String uuid, @RequestBody SignupRequestDto signupRequestDTO) {
        try {
            userService.updateUser(uuid, signupRequestDTO);

            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User update failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{uuid}/nickname")
    public ResponseEntity<?> getNicknameByUuid(@PathVariable String uuid){
        String nickname = userService.getNicknameByUuid(uuid);
        return ResponseEntity.ok(nickname);
    }
}