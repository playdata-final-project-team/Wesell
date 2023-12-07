package com.wesell.adminservice.dto.response;

import com.wesell.adminservice.error.InternalServerException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ErrorResponseDto {

    private final String timestamp;
    private final String trackingId;
    private final HttpStatus status;
    private final String code;
    private final List<Object> message;
    private final String detailMessage;

    public ErrorResponseDto(InternalServerException e) {
        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.status = e.getErrorCode().getStatus();
        this.code = e.getClass().getSimpleName();
        this.message = List.of(e.getErrorCode().getMessage());
        this.detailMessage = e.getMessage();
    }
}