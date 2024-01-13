package com.wesell.payservice.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseDetailDto {
    private ResponsePayDto payDto;
    private ResponseDeliveryDto deliveryDto;
}
