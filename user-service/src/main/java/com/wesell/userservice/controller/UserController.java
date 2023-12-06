package com.wesell.userservice.controller;

import com.wesell.userservice.dto.request.SignupRequestDto;
import com.wesell.userservice.dto.response.ResponseDto;
import com.wesell.userservice.exception.UserNotFoundException;
import com.wesell.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("user")
    public ResponseEntity<ResponseDto> findUserResponseEntity(@RequestParam("uuid") String uuid) {

        try {
            return ResponseEntity.ok(userService.findUser(uuid));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("users")
    public ResponseEntity<List<ResponseDto>> findUsersResponseEntity() {

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
        }
    }

    @PostMapping("api/sign-up")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDTO) {

        try {
            userService.save(signupRequestDTO);
            return new ResponseEntity<>("Signup successful", HttpStatus.OK);
        } catch (Exception e) {
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

    @GetMapping("users/{uuid}/nickname")
    public ResponseEntity<String> getNicknameByUuid(@PathVariable(value = "uuid") String uuid) {
        Optional<String> nicknameOptional = userService.getNicknameByUuid(uuid);
        return nicknameOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
