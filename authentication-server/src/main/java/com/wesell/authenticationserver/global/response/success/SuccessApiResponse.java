package com.wesell.authenticationserver.global.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wesell.authenticationserver.global.response.ApiResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SuccessApiResponse<T> extends ApiResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T content;

    private SuccessApiResponse(SuccessCode successCode, T content){
        super(
                LocalDateTime.now().toString(),
                successCode.getStatus().toString(),
                successCode.getCode(),
                successCode.getMessage()
        );
        this.content = content;
    }

    private SuccessApiResponse(SuccessCode successCode){
        this(successCode, null);
    }

    public static <T> SuccessApiResponse<T> of(SuccessCode successCode){
        return new SuccessApiResponse<>(successCode);
    }

    public static <T> SuccessApiResponse<T> of(SuccessCode successCode, T content){
        return new SuccessApiResponse<>(successCode, content);
    }
}
