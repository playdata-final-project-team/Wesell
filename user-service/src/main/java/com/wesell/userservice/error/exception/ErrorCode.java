package com.wesell.userservice.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMON-ERR-404", "페이지를 찾을 수 없습니다."),
    INTER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "유저 정보를 찾을 수 없습니다."),
    NOT_FOUND_PHONE(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "전화번호가 일치하지 않습니다."),
    NOT_FOUND_NICKNAME(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "닉네임을 찾을 수 없습니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST,"DN","중복된 닉네임 입니다.");



    private final HttpStatus status;
    private final String errorCode;
    private final String message;

}