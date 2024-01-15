package com.wesell.payservice.service.delivery;

import com.wesell.payservice.domain.dto.request.DeliveryRequestDto;
import com.wesell.payservice.domain.dto.response.DeliveryResponseDto;
import com.wesell.payservice.domain.entity.Delivery;
import com.wesell.payservice.domain.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class DeliveryServiceImpl implements DeliveryService{
    private final DeliveryRepository deliveryRepository;
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Long createDelivery(DeliveryRequestDto requestDto) {
        Delivery delivery = Delivery.createDelivery(requestDto);
        deliveryRepository.save(delivery);
        return delivery.getId();
    }

    @Override
    public DeliveryResponseDto getDeliveryInfo(Long id) {
        Delivery delivery = deliveryRepository.findDeliveryById(id);
        return new DeliveryResponseDto(delivery);
    }

    @Override
    public void startDelivery(Integer shippingNumber, Long deliveryId) {
        Delivery delivery = deliveryRepository.findDeliveryById(deliveryId);
        delivery.startDelivery(shippingNumber);
    }

    @Override
    public void finishDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findDeliveryById(deliveryId);
        delivery.finishDelivery();
    }
}
