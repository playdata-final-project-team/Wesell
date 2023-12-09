package com.wesell.userservice.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    OK(HttpStatus.OK, "OK", "요청 성공하셨습니다."),
    USER_CREATED(HttpStatus.CREATED, "UC", "회원가입 성공하셨습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
