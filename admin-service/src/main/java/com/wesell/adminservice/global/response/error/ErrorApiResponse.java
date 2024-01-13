package com.wesell.adminservice.global.response.error;

import com.wesell.adminservice.global.response.ApiResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ErrorApiResponse<T> extends ApiResponse {

    private ErrorApiResponse(CustomException e){
        super(
                LocalDateTime.now().toString(),
                e.getErrorCode().getStatus().toString(),
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage()
        );
    }

    public static <T> ErrorApiResponse<T> of(CustomException e){
        return new ErrorApiResponse<>(e);
    }
}
