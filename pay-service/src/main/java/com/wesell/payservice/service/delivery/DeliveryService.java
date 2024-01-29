package com.wesell.payservice.service.delivery;

import com.wesell.payservice.domain.dto.request.DeliveryRequestDto;
import com.wesell.payservice.domain.dto.request.DeliveryUpdateRequestDto;
import com.wesell.payservice.domain.dto.request.StartDeliveryRequestDto;
import com.wesell.payservice.domain.dto.response.DeliveryResponseDto;

public interface DeliveryService {
    Long createDelivery(DeliveryRequestDto requestDto);
    DeliveryResponseDto getDeliveryInfo(Long id);
    DeliveryResponseDto updateDeliveryInfo(DeliveryUpdateRequestDto requestDto);
    void startDelivery(StartDeliveryRequestDto requestDto);
    void finishDelivery(Long deliveryId);
    void finishDeliveryAll();
}
