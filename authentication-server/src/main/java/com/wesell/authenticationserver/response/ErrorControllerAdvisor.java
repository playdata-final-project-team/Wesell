package com.wesell.authenticationserver.response;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorControllerAdvisor {

    /**
     * springframework.validation 에러 메시지 처리
     * @return ResponseEntity<String>
     */
    @ExceptionHandler(value = BindException.class)
    protected ResponseEntity<String> handleBindException(BindException e){

        StringBuilder errorMessage = new StringBuilder("필수 입력 항목을 전부 입력해주세요! : \n");

        for (FieldError fieldError : e.getFieldErrors()) {
            errorMessage.append(fieldError.getField())
                    .append(" - ")
                    .append(fieldError.getDefaultMessage())
                    .append("\n");
        }

        return ResponseEntity.status(400).body(errorMessage.toString());
    }

    /**
     * Custom 예외 처리
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e){

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorResponseDto.of(e));

    }
}
