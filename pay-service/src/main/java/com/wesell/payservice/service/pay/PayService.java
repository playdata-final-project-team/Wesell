package com.wesell.payservice.service.interface_;

import com.wesell.payservice.domain.dto.request.RequestPayDto;
import com.wesell.payservice.domain.dto.response.ResponsePayDto;

public interface PayService {
    Long pay(RequestPayDto requestDto);
    String createOrderNumber(RequestPayDto requestDto);
    ResponsePayDto getPayResult(Long payId);
}
