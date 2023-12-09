package com.wesell.adminservice.error;

import lombok.Getter;

@Getter
public class InternalServerException extends RuntimeException{

    ErrorCode errorCode;

    public InternalServerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
