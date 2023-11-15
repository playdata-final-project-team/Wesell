package com.wesell.authenticationserver.response;

import lombok.*;

/**
 * 에러 메시지 응답 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDto {

    private int status;
    private String code;
    private String message;
    private String detail; // exception 발생 시 오류 메시지

    private ErrorResponseDto(ErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ErrorResponseDto of(ErrorCode errorCode){
        return new ErrorResponseDto(errorCode);
    }

}
