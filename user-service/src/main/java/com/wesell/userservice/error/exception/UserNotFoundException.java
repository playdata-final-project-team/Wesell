package com.wesell.userservice.error.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    ErrorCode errorCode;

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
