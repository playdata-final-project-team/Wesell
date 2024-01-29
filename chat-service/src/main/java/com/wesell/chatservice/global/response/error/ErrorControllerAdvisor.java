package com.wesell.chatservice.global.response.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Log4j2
public class ErrorControllerAdvisor {

    /**
     * 유효성 검증 에러 메시지 처리
     * @return ResponseEntity<String>
     */
    @ExceptionHandler(value = BindException.class)
    protected ResponseEntity<?> handleBindException(BindException e){
        log.error("[Error] 에러 발생 ! errorCode : {}, errorMessage : {}",e.getMessage());
        List<String> errorMessageList = e.getFieldErrors().stream().map(
                b->b.getField() + " : " + b.getDefaultMessage()
        ).toList();
        return ResponseEntity.status(ErrorCode.VALIDATION_FAIL.getStatus())
                .body(ErrorApiResponse.of(e,errorMessageList));
    }

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
