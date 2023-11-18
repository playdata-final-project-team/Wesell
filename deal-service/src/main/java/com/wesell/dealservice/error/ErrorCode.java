package com.wesell.dealservice.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * create posting
     */
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다.");

    private final HttpStatus status;
    private final String message;
}