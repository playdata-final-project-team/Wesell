package com.wesell.payservice.service.facade;

import com.wesell.payservice.domain.dto.request.DeliveryUpdateRequestDto;
import com.wesell.payservice.domain.dto.response.DetailFacadeResponseDto;
import com.wesell.payservice.domain.dto.response.PayResponseDto;
import com.wesell.payservice.domain.dto.search.PayViewDao;
import com.wesell.payservice.service.delivery.DeliveryServiceImpl;
import com.wesell.payservice.service.pay.PayServiceImpl;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class FacadeService {
    private final PayServiceImpl payService;
    private final DeliveryServiceImpl deliveryService;
    private final PayViewDao payViewDao;
    public FacadeService(PayServiceImpl payService, DeliveryServiceImpl deliveryService, PayViewDao payViewDao) {
        this.payService = payService;
        this.deliveryService = deliveryService;
        this.payViewDao = payViewDao;
    }

    public DetailFacadeResponseDto getPayResult(Long payId) {
        DetailFacadeResponseDto dto = new DetailFacadeResponseDto();
        dto.setDeliveryDto(deliveryService.getDeliveryInfo(payService.findDeliveryByPayId(payId)));
        dto.setPayDto(new PayResponseDto(payViewDao.searchPayById(payId)));
        return dto;
    }

}
