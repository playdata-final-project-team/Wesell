package com.wesell.authenticationserver.error;

import lombok.Getter;

/**
 * 예외 일괄 처리 : 커스텀 에러코드  - 구현중
 */
@Getter
public enum ErrorCode {

    /**
     * common
     */
    INVALID_REQUEST(400,"C001","유효하지 않은 요청입니다."),
    INVALID_RESPONSE(400,"C002","유효하지 않은 응답입니다."),
    NOT_FOUND_RESOURCE(404,"C003","찾을 수 없는 리소스입니다."),
    TEMPORARY_SERVER_ERROR(400,"C004","일시적으로 서버 내 오류가 발생했습니다.");

    /**
     *  user
     */


    private final int status; // 상태코드(숫자)
    private final String code; // 커스텀 코드(서버 내 관리하는 에러 코드)
    private final String message; // 에러 발생 시 메시지

    private ErrorCode(int status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
