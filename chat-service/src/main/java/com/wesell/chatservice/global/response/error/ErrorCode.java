package com.wesell.chatservice.global.response.error;

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
    // Validation Fail
    VALIDATION_FAIL(HttpStatus.BAD_REQUEST,"VF","유효성 검증 실패하였습니다."),

    /**
     * chat
     */
    // Chat Room Not Found
    CHATROOM_NOT_FOUND(HttpStatus.BAD_REQUEST,"CNF","채팅방을 찾을 수 없습니다."),

    /**
     * feign 관련 오류
     */
    USER_SERVICE_FEIGN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"UFE","유저 서비스로 Feign 요청 시 오류 발생"),
    IMAGE_SERVER_FEIGN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"IFE","이미지 서버로 Feign 요청 시 오류 발생");

    private final HttpStatus status; // 상태코드(숫자)
    private final String code; // 커스텀 코드(서버 내 관리하는 에러 코드)
    private final String message; // 에러 발생 시 메시지

    private ErrorCode(HttpStatus status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
