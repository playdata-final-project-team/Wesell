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
    PAY_NOT_FOUND(HttpStatus.NOT_FOUND,"NF","구매 내역이 없습니다."),
    REJECT_REASON_SOLD_OUT(HttpStatus.LOCKED, "RRSO", "판매가 완료된 상품입니다."),
    /**
     * Delivery
     */
    NOT_INVALID_READ(HttpStatus.LOCKED, "NIR", "버전이 맞지 않아 조회가 불가합니다."),
    NOT_INVALID_UPDATE(HttpStatus.LOCKED, "NIU", "버전이 맞지 않아 수정이 불가합니다"),
    CANNOT_UPDATE_INFO(HttpStatus.FORBIDDEN, "CUI", "이미 배송이 시작되었습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}