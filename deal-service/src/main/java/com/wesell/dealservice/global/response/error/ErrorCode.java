package com.wesell.dealservice.global.response.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * category
     */
    DUPLICATED_REQUEST(HttpStatus.BAD_REQUEST, "400", "이미 존재하는 카테고리입니다."),
    /**
     * posting
     */
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "400", "유효하지 않은 요청입니다."),
    POST_NOT_FOUND(HttpStatus.NO_CONTENT, "404", "검색 결과가 없습니다."),
    INVALID_POST(HttpStatus.BAD_REQUEST, "400", "잘못된 게시물입니다."),
    /**
     * searching
     */
    NO_PRICE_RESEARCH(HttpStatus.NOT_FOUND, "404", "검색 결과가 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}