package com.wesell.chatservice.global.response.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    /**
     * Common Success
     */
    OK(HttpStatus.OK,"OK","요청 성공하셨습니다."),
    USER_CREATED(HttpStatus.CREATED,"UC","회원가입 성공하셨습니다.");

    private final HttpStatus status; // Http 상태코드(숫자)
    private final String code; // Custom 응답코드
    private final String message; // 응답 메시지
}
