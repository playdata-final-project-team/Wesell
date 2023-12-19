package com.wesell.userservice.controller.response;

import com.wesell.userservice.error.exception.ErrorCode;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class CustomException extends RuntimeException{

    ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String detailMessage){
        super(detailMessage);
        this.errorCode = errorCode;
    }
}
