package com.wesell.authenticationserver.controller.response;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ErrorControllerAdvisor {

    /**
     * 유효성 검증 에러 메시지 처리
     * @return ResponseEntity<String>
     */
    @ExceptionHandler(value = BindException.class)
    protected ResponseEntity<ResponseDto> handleBindException(BindException e){
        e.getFieldErrors().stream().forEach(
            b-> log.error("[ValidationFail] 오류 메시지 : field:{}, message: {}"
                    ,b.getField(),b.getDefaultMessage())
        );
        return ResponseEntity.status(ErrorCode.VALIDATION_FAIL.getStatus())
                .body(ResponseDto.of(e));
    }

    /**
     * Custom 예외 처리
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<ResponseDto> handleCustomException(CustomException e){
        log.error("[Error] 에러 발생 ! errorCode : {}, errorMessage : {}",e.errorCode,e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ResponseDto.of(e));
    }
}
