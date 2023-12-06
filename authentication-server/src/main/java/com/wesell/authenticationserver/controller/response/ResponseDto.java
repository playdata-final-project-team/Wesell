package com.wesell.authenticationserver.controller.response;

import lombok.*;
import java.time.LocalDateTime;

/**
 * 에러 메시지 응답 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {

    private String timeStamp;
    private String status;
    private String code;
    private String message;
    private String detail; // exception 발생 시 오류 메시지

    private ResponseDto(CustomException e){
        this.timeStamp = LocalDateTime.now().toString();
        this.status = e.getErrorCode().getStatus().toString();
        this.code = e.getErrorCode().getCode();
        this.message = e.getErrorCode().getMessage();
        this.detail = e.getMessage();
    }

    private ResponseDto(SuccessCode code, String detail){
        this.timeStamp = LocalDateTime.now().toString();
        this.status = code.getStatus().toString();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.detail = detail;
    }

    public static ResponseDto of(CustomException e){
        return new ResponseDto(e);
    }

    public static ResponseDto of(SuccessCode code, String detail){
        return new ResponseDto(code, detail);
    }

}
