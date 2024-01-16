package com.wesell.payservice.global.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wesell.payservice.global.response.ApiResponse;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SuccessApiResponse<T> extends ApiResponse {
    @JsonInclude(Include.NON_NULL)
    private T content;

    private SuccessApiResponse(SuccessCode successCode, T content) {
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
