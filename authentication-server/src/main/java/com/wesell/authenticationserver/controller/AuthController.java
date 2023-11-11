package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.service.AuthUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody CreateUserRequestDto dto){

        log.debug("AuthController - 회원가입");

        authUserService.createUser(dto);

        return ResponseEntity.status(201).body(null);
    }
}
