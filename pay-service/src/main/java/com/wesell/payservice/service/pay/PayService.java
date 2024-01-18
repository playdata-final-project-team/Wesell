package com.wesell.payservice.service.pay;

import com.wesell.payservice.domain.dto.request.PayRequestDto;
import com.wesell.payservice.domain.dto.response.MyPayListResponseDto;
import org.springframework.data.domain.Page;

public interface PayService {
    Long createPay(PayRequestDto requestDto);
    String createOrderNumber(PayRequestDto requestDto);
    Page<MyPayListResponseDto> getMyPayList(String buyer, int page);
}
