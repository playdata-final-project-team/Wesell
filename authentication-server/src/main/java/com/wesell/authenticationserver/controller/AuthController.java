package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.service.AuthUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("auth-server")
public class AuthController {

    private final AuthUserService authUserService;

    @GetMapping("health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok().body("auth-server available");
    }

    @PostMapping("sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody CreateUserRequestDto dto){

        log.debug("AuthController - 회원가입");

        authUserService.createUser(dto);

        return ResponseEntity.status(201).body(null);
    }
}
