package com.wesell.dealservice.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ApiResponse {
    protected String timeStamp;
    protected String status;
    protected String code;
    protected String message;
}
