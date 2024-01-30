package com.wesell.payservice.service.delivery;

import com.wesell.payservice.domain.dto.request.DeliveryRequestDto;
import com.wesell.payservice.domain.dto.request.DeliveryUpdateRequestDto;
import com.wesell.payservice.domain.dto.request.StartDeliveryRequestDto;
import com.wesell.payservice.domain.dto.response.DeliveryResponseDto;
import com.wesell.payservice.domain.entity.Delivery;
import com.wesell.payservice.domain.repository.DeliveryRepository;
import com.wesell.payservice.domain.repository.OptimisticRepository;
import com.wesell.payservice.global.response.error.ErrorCode;
import com.wesell.payservice.global.response.error.exception.CustomException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class DeliveryServiceImpl implements DeliveryService{
    private final DeliveryRepository deliveryRepository;
    private final OptimisticRepository optimisticRepository;
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, OptimisticRepository optimisticRepository) {
        this.deliveryRepository = deliveryRepository;
        this.optimisticRepository = optimisticRepository;
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
    public DeliveryResponseDto updateDeliveryInfo(DeliveryUpdateRequestDto requestDto) {
        Delivery delivery = optimisticRepository.findByIdOptimisticLockMode(requestDto.getId());

        if(delivery.getShippingNumber() != null){
            throw new CustomException(ErrorCode.CANNOT_UPDATE_INFO);
        } else if(!delivery.getVersion().equals(requestDto.getVersion())) {
            throw new CustomException(ErrorCode.NOT_INVALID_UPDATE);
        }

        delivery.updateDelivery(requestDto);
        return new DeliveryResponseDto(delivery);
    }

    @Override
    public void startDelivery(StartDeliveryRequestDto requestDto) {
        Delivery delivery = deliveryRepository.findDeliveryById(requestDto.getDeliveryId());

        if(!delivery.getVersion().equals(requestDto.getVersion())) {
            throw new CustomException(ErrorCode.NOT_INVALID_READ);
        }

        delivery.startDelivery(requestDto.getShippingNumber());
    }

    @Override
    public void finishDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findDeliveryById(deliveryId);
        delivery.finishDelivery();
    }

    @Override
    public void finishDeliveryAll() {
        LocalDate deadline = LocalDate.now().minusDays(10);
        List<Delivery> deliveries = deliveryRepository.findDeliveryByCreatedAt(deadline);
        deliveries.forEach(Delivery::finishDelivery);
    }
}
