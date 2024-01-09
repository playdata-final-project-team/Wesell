package com.wesell.authenticationserver.controller.response;

import lombok.*;
import org.springframework.validation.BindException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 에러 메시지 응답 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseDto<T> {

    private String timeStamp; // 응답 시간
    private String status; //  응답 상태 코드
    private String code; // 커스텀 코드
    private String message; // 메시지
    private T dto;
    private List<String> vfMessages; // validation 에러 메시지

    // 전체 예외 처리
    public ResponseDto(CustomException e, T dto){
        this.timeStamp = LocalDateTime.now().toString();
        this.status = e.getErrorCode().getStatus().toString();
        this.code = e.getErrorCode().getCode();
        this.message = e.getErrorCode().getMessage();
        this.dto = dto;
    }

    private ResponseDto(BindException e){
        this.timeStamp = LocalDateTime.now().toString();
        this.status = ErrorCode.VALIDATION_FAIL.getStatus().toString();
        this.code = ErrorCode.VALIDATION_FAIL.getCode();
        this.message = ErrorCode.VALIDATION_FAIL.getMessage();
        this.vfMessages = e.getFieldErrors().stream().map(
                b->b.getField() + " : " + b.getDefaultMessage()
        ).toList();
    }

    private ResponseDto(SuccessCode code){
        this.timeStamp = LocalDateTime.now().toString();
        this.status = code.getStatus().toString();
        this.code = code.getCode();
        this.message = code.getMessage();
    }

//    public static ResponseDto of(CustomException e){
//        return new ResponseDto(e);
//    }

    public static ResponseDto of(BindException e){ return new ResponseDto(e);}

    public static ResponseDto of(SuccessCode code){
        return new ResponseDto(code);
    }

    public static ResponseDto of(SuccessCode code, String message){
        ResponseDto responseDto = new ResponseDto(code);
        responseDto.setMessage(message);
        return responseDto;
    }
}
