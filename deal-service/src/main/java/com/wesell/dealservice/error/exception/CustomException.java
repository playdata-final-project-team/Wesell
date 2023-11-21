package com.wesell.dealservice.error.exception;

import com.wesell.dealservice.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
