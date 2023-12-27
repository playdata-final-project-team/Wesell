package com.wesell.apigatewayserver.response;

import com.wesell.apigatewayserver.response.exception.CustomException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 에러 메시지 응답 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDto {

    private String timeStamp; // 응답 시간
    private String status; //  응답 상태 코드
    private String code; // 커스텀 코드
    private String message; // 메시지
    private List<String> vfMessages; // validation 에러 메시지

    private ErrorResponseDto(CustomException e){
        this.timeStamp = LocalDateTime.now().toString();
        this.status = e.getErrorCode().getStatus().toString();
        this.code = e.getErrorCode().getCode();
        this.message = e.getErrorCode().getMessage();
    }

    public static ErrorResponseDto of(CustomException e){
        return new ErrorResponseDto(e);
    }

}
