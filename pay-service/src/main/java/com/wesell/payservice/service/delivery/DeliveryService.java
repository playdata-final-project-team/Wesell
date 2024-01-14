package com.wesell.payservice.service.delivery;

import com.wesell.payservice.domain.dto.request.RequestDeliveryDto;
import com.wesell.payservice.domain.dto.response.ResponseDeliveryDto;

public interface DeliveryService {
    Long createDelivery(RequestDeliveryDto requestDto);
    ResponseDeliveryDto getDeliveryInfo(Long id);
    void startDelivery(Integer shippingNumber, Long deliveryId);
    void finishDelivery(Long deliveryId);
}
