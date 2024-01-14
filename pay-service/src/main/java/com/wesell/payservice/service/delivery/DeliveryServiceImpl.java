package com.wesell.payservice.service.delivery;

import com.wesell.payservice.domain.dto.request.RequestDeliveryDto;
import com.wesell.payservice.domain.dto.request.RequestPayDto;
import com.wesell.payservice.domain.dto.response.ResponseDeliveryDto;
import com.wesell.payservice.domain.entity.Delivery;
import com.wesell.payservice.domain.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    public Long createDelivery(RequestDeliveryDto requestDto) {
        Delivery delivery = Delivery.createDelivery(requestDto);
        deliveryRepository.save(delivery);
        return delivery.getId();
    }

    @Override
    public ResponseDeliveryDto getDeliveryInfo(Long id) {
        Delivery delivery = deliveryRepository.findDeliveryById(id);
        return new ResponseDeliveryDto(delivery);
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
