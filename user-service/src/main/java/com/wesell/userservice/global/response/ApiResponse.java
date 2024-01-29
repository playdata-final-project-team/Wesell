package com.wesell.userservice.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class ApiResponse {
    protected String timeStamp; // 응답 시간
    protected String status; //  응답 상태 코드
    protected String code; // 커스텀 코드
    protected String message; // 메시지
}
