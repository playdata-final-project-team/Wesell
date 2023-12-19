
package com.wesell.userservice.controller.response;

import com.wesell.userservice.error.exception.SuccessCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewResponseDto {

    private String timeStamp; // 응답 시간
    private String status; //  응답 상태 코드
    private String code; // 커스텀 코드
    private String message; // 메시지
    private List<String> vfMessages; // validation 에러 메시지

    // 전체 예외 처리
    private NewResponseDto(CustomException e){
        this.timeStamp = LocalDateTime.now().toString();
        this.status = e.getErrorCode().getStatus().toString();
        this.code = e.getErrorCode().getErrorCode();
        this.message = e.getErrorCode().getMessage();
    }

    private NewResponseDto(SuccessCode code){
        this.timeStamp = LocalDateTime.now().toString();
        this.status = code.getStatus().toString();
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public static NewResponseDto of(CustomException e){
        return new NewResponseDto(e);
    }

    public static NewResponseDto of(SuccessCode code){
        return new NewResponseDto(code);
    }

    public static NewResponseDto of(SuccessCode code, String message){
        NewResponseDto newResponseDto = new NewResponseDto(code);
        newResponseDto.setMessage(message);
        return newResponseDto;
    }
}
