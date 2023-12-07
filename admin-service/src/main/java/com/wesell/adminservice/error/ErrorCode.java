package com.wesell.adminservice.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMON-ERR-404", "페이지를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR_GET_USER_LIST(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "유저 전체 목록 조회 오류 발생"),
    INTERNAL_SERVER_ERROR_CHANGE_USER_ROLE(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "회원 권한 변경 오류 발생"),
    INTERNAL_SERVER_ERROR_GET_POST_LIST(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "글 목록 조회 오류 발생"),
    INTERNAL_SERVER_ERROR_USER_ISFORCED(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "회원 강제 탈퇴 오류 발생"),
    INTERNAL_SERVER_ERROR_POST_ISDELETED(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "글 강제 삭제 오류 발생"),
    INTERNAL_SERVER_ERROR_SEARCH_USERS(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-ERR-500", "회원 목록 검색 오류 발생");


    private final HttpStatus status;
    private final String errorCode;
    private final String message;

}