package com.wesell.chatservice.global.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wesell.chatservice.global.response.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindException;

import java.time.LocalDateTime;
@Getter
@Setter
public class ErrorApiResponse<T> extends ApiResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T content;

    private ErrorApiResponse(CustomException e){
        super(
                LocalDateTime.now().toString(),
                e.getErrorCode().getStatus().toString(),
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage()
        );
    }

    private ErrorApiResponse(BindException e, T content){
        super(
                LocalDateTime.now().toString(),
                ErrorCode.VALIDATION_FAIL.getStatus().toString(),
                ErrorCode.VALIDATION_FAIL.getCode(),
                ErrorCode.VALIDATION_FAIL.getMessage()
        );
        this.content = content;
    }

    public static <T> ErrorApiResponse<T> of(CustomException e){
        return new ErrorApiResponse<>(e);
    }

    public static <T> ErrorApiResponse<T> of(BindException e, T content){
        return new ErrorApiResponse<>(e, content);
    }
}
