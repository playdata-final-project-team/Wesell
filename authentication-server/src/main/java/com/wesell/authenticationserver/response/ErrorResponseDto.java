package com.wesell.authenticationserver.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * 에러 메시지 응답 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDto {

    private String timeStamp;
    private HttpStatus status;
    private String code;
    private String message;
    private String detail; // exception 발생 시 오류 메시지

    private ErrorResponseDto(CustomException e){
        this.timeStamp = LocalDateTime.now().toString();
        this.status = e.getErrorCode().getStatus();
        this.code = e.getErrorCode().getCode();
        this.message = e.errorCode.getMessage();
        this.detail = e.getMessage();
    }

    public static ErrorResponseDto of(CustomException e){
        return new ErrorResponseDto(e);
    }

}
