package com.wesell.payservice.domain.dto.response;

import com.wesell.payservice.enumerate.ShippingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePayFacadeDto {
    private ResponsePayDto payDto;
    private ShippingStatus status;
}
