package com.wesell.dealservice.dto.response;

import com.wesell.dealservice.error.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ErrorResponseDto {

    private final String timeStamp;
    private final String trackingId;
    private final HttpStatus status;
    private final String code;
    private final String detailMessage;

    public ErrorResponseDto(CustomException e) {
        this.timeStamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.status = e.getErrorCode().getStatus();
        this.code = e.getClass().getSimpleName();
        this.detailMessage = e.getMessage();
    }

}
