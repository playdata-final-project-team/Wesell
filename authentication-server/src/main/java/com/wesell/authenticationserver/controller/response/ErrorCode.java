package com.wesell.authenticationserver.controller.response;

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
    // TSE
    TEMPORARY_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"TSE","일시적으로 서버 내 오류가 발생했습니다."),

    /**
     *  auth
     */
    // Validation Fail
    VALIDATION_FAIL(HttpStatus.BAD_REQUEST,"VF","유효성 검증 실패하였습니다."),
    // Not-Found-User
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST,"NFU","가입 되지 않은 회원입니다."),
    // Password-Not-Correct
    NOT_CORRECT_PASSWORD(HttpStatus.BAD_REQUEST,"NCP","비밀번호가 일치하지 않습니다."),
    // Duplicated-Email
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST,"DE","이미 회원 가입된 이메일입니다."),

    /**
     * token
     */
    // Access-Token Invalid
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"IAT","유효 하지 않은 JWT token 입니다."),
    // Refresh-token Invalid
    INVALID_REFRESH_TOKEN(HttpStatus.FORBIDDEN,"IRT","유효 하지 않은 Refresh token 입니다."),
    // Expired Jwt
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"EAT","JWT 만료 기한이 지났습니다."),

    /**
     * user 관련 오류
     */

    /**
     * 소셜 로그인 계정 비밀번호 찾기
     */

    /**
     * feign 관련 오류
     */
    USER_SERVICE_FEIGN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"UFE","유저 서비스로 Feign 요청 시 오류 발생"),
    // SMS 인증 실패 오류
    SMS_VALID_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"SVF","SMS 관련 API 요청 시 오류 발생");


    private final HttpStatus status; // 상태코드(숫자)
    private final String code; // 커스텀 코드(서버 내 관리하는 에러 코드)
    private final String message; // 에러 발생 시 메시지

    private ErrorCode(HttpStatus status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
