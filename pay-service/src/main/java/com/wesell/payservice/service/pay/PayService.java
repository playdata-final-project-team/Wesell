package com.wesell.payservice.service.pay;

import com.wesell.payservice.domain.dto.request.PayRequestDto;
import com.wesell.payservice.domain.dto.response.PayResponseDto;

public interface PayService {
    Long createPay(PayRequestDto requestDto);
    String createOrderNumber(PayRequestDto requestDto);
    PayResponseDto getPayInfo(Long payId);
}
