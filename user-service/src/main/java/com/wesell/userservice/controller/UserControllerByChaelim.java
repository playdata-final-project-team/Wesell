package com.wesell.userservice.controller;

import com.wesell.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RestController
@RequestMapping("user-service")
@RequiredArgsConstructor
public class UserControllerByChaelim {
    private final UserService userService;

    @GetMapping("users/{uuid}/nickname")
    public ResponseEntity<String> getNicknameByUuid(@PathVariable(value = "uuid") String uuid) {
        Optional<String> nicknameOptional = userService.getNicknameByUuid(uuid);
        return nicknameOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
