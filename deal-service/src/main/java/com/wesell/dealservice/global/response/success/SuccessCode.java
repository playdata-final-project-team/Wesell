package com.wesell.dealservice.global.response.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class SuccessCode {

    private final HttpStatus status;
    private final String code;
    private final String message;
}
