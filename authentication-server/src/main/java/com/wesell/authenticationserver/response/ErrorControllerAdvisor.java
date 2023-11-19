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
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<String> handlerBindException(BindException e){

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
    @ExceptionHandler(CustomException.class)
    protected ErrorResponseDto handlerCustomException(CustomException e){
        return ErrorResponseDto.of(e);
    }
}
