package com.wesell.boardservice.domain.dto.reponse;

import com.wesell.boardservice.error.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class ErrorResponseDto {

    private final String timestamp;
    private final String trackingId;
    private final HttpStatus status;
    private final String code;
    private final List<Object> message;
    private final String detailMessage;

    public ErrorResponseDto(CustomException e) {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.status = e.getErrorCode().getStatus();
        this.code = e.getClass().getSimpleName();
        this.message = List.of(e.getErrorCode().getMessage());
        this.detailMessage = e.getMessage();
    }
}
