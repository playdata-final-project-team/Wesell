package com.wesell.payservice.service.pay;

import com.wesell.payservice.domain.dto.request.RequestPayDto;
import com.wesell.payservice.domain.dto.response.ResponsePayDto;

public interface PayService {
    Long createPay(RequestPayDto requestDto);
    String createOrderNumber(RequestPayDto requestDto);
    ResponsePayDto getPayInfo(Long payId);
}
