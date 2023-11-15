package com.wesell.authenticationserver.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    OK(200),
    USER_CREATED(201);

    private final int status; // 상태코드(숫자)

}
