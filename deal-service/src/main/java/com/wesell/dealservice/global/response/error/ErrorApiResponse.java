package com.wesell.dealservice.global.response.error;

import com.wesell.dealservice.global.response.ApiResponse;
import com.wesell.dealservice.global.response.error.exception.CustomException;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorApiResponse<T> extends ApiResponse {
    public ErrorApiResponse(CustomException e) {
        super(LocalDateTime.now().toString(),
                e.getErrorCode().getStatus().toString(),
                e.getErrorCode().getCode(),
                e.getMessage());
    }

    public static <T> ErrorApiResponse<T> of(CustomException e ){
        return new ErrorApiResponse<>(e);
    }
}
