package com.wesell.userservice.global.response.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 예외 일괄 처리 : 커스텀 에러코드  - 구현중
 */
@Getter
public enum ErrorCode {

    /**
     * common
     */
    // TSE
    TEMPORARY_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"TSE","일시적으로 서버 내 오류가 발생했습니다."),

    /**
     * user 관련 오류
     */
    // Not-Found-User
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST,"NFU","가입 되지 않은 회원입니다."),
    // Duplicated-Nickname
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST,"DN","중복된 닉네임입니다."),

    /**
     * feign 관련 오류
     */
    AUTH_SERVICE_FEIGN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"UFE","유저 서비스로 Feign 요청 시 오류 발생");

    private final HttpStatus status; // 상태코드(숫자)
    private final String code; // 커스텀 코드(서버 내 관리하는 에러 코드)
    private final String message; // 에러 발생 시 메시지

    private ErrorCode(HttpStatus status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
