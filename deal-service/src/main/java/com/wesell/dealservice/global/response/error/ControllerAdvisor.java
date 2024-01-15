package com.wesell.dealservice.global.response.error;

import com.wesell.dealservice.global.response.error.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<?> customExceptionHandler(CustomException e) {
        log.error("Error occurred in controller advice! errorCode: {}, errorMessage: {}", e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ErrorApiResponse.of(e));
    }

}
