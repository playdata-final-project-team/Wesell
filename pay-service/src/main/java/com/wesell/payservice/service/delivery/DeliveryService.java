package com.wesell.payservice.service.delivery;

import com.wesell.payservice.domain.dto.request.DeliveryRequestDto;
import com.wesell.payservice.domain.dto.response.DeliveryResponseDto;

public interface DeliveryService {
    Long createDelivery(DeliveryRequestDto requestDto);
    DeliveryResponseDto getDeliveryInfo(Long id);
    void startDelivery(Integer shippingNumber, Long deliveryId);
    void finishDelivery(Long deliveryId);
}
