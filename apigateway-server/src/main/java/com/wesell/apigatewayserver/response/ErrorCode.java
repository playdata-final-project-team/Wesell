package com.wesell.apigatewayserver.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 예외 일괄 처리 : 커스텀 에러코드  - 구현중
 */
@Getter
public enum ErrorCode {

    /**
     * common
     */
    INVALID_REQUEST(HttpStatus.BAD_REQUEST,"C001","유효하지 않은 요청입니다."),
    INVALID_RESPONSE(HttpStatus.BAD_REQUEST,"C002","유효하지 않은 응답입니다."),
    NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND,"C003","찾을 수 없는 리소스입니다."),
    TEMPORARY_SERVER_ERROR(HttpStatus.BAD_REQUEST,"C004","일시적으로 서버 내 오류가 발생했습니다."),

    /**
     * token
     */
    // Access-Token Invalid
    INVALID_ACCESS_TOKEN(HttpStatus.FORBIDDEN,"IAT","유효 하지 않은 Access token 입니다."),
    // Refresh-token Invalid
    INVALID_REFRESH_TOKEN(HttpStatus.FORBIDDEN,"IRT","유효 하지 않은 Refresh token 입니다."),
    // Expired Jwt
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"EAT","JWT 만료 기한이 지났습니다.");


    private final HttpStatus status; // 상태코드(숫자)
    private final String code; // 커스텀 코드(서버 내 관리하는 에러 코드)
    private final String message; // 에러 발생 시 메시지

    private ErrorCode(HttpStatus status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
