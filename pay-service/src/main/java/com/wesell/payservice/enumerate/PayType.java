package com.wesell.payservice.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PayType {
    ASSURED("안전결제", 0),
    UNASSURED("자유결제", 1);

    private final String name;
    private final Integer code;
}
