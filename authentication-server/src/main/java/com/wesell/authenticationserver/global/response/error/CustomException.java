package com.wesell.authenticationserver.global.response.error;

import com.wesell.authenticationserver.global.response.error.ErrorCode;
import lombok.Getter;

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
