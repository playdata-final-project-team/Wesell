package com.wesell.payservice.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetailFacadeResponseDto {
    private PayResponseDto payDto;
    private DeliveryResponseDto deliveryDto;
}
