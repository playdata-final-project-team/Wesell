package com.wesell.payservice.global.response.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * Pay
     */
    PAY_NOT_FOUND(HttpStatus.NOT_FOUND,"NF","구매 내역이 없습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}