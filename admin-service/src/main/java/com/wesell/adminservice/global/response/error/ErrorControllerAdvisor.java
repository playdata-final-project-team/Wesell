package com.wesell.adminservice.global.response.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ErrorControllerAdvisor {

    /**
     * Custom 예외 처리
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<?> handleCustomException(CustomException e){
        log.error("[Error] 에러 발생 ! errorCode : {}, errorMessage : {}",e.errorCode,e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorApiResponse.of(e));
    }
}
