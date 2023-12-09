package com.wesell.userservice.error.exception;

import com.wesell.userservice.dto.response.ErrorResponseDto;
import com.wesell.userservice.error.exception.ErrorCode;
import com.wesell.userservice.error.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> userExceptionHandler(UserNotFoundException e) {
        ErrorResponseDto dto = new ErrorResponseDto(e);
        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(dto);
    }


}