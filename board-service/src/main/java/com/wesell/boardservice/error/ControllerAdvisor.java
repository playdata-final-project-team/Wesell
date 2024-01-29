package com.wesell.boardservice.error;

import com.wesell.boardservice.domain.dto.reponse.ErrorResponseDto;
import com.wesell.boardservice.error.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseDto> customExceptionHandler(CustomException e) {
        ErrorResponseDto dto = new ErrorResponseDto(e);
        log.error("Error occurred in controller advice: [id={}]", dto.getTrackingId());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(dto);
    }
}
