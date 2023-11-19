package com.wesell.authenticationserver.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        this(errorCode,"");
    }

    public CustomException(ErrorCode errorCode, String detailMessage){
        super(detailMessage);
        this.errorCode = errorCode;
    }
}
