package com.wesell.boardservice.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "400", "존재하지 않는 게시물입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
