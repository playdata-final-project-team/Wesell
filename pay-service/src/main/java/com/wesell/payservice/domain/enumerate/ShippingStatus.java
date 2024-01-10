package com.wesell.payservice.domain.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ShippingStatus {

    PREPARING("배송 준비 중", 100),
    SHIPPING("배송 중", 200),
    COMPLETE("배송 완료",300);

    private final String name;
    private final Integer code;
}
