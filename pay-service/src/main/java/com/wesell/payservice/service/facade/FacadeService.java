package com.wesell.payservice.service.facade;

import com.wesell.payservice.domain.dto.request.RequestPayDto;
import com.wesell.payservice.domain.dto.response.ResponsePayFacadeDto;
import com.wesell.payservice.enumerate.ShippingStatus;
import com.wesell.payservice.service.implement_.PayServiceImpl;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class FacadeService {
    private final PayServiceImpl payService;

    public FacadeService(PayServiceImpl payService) {
        this.payService = payService;
    }

    public ResponsePayFacadeDto pay(RequestPayDto requestDto) {
        ResponsePayFacadeDto responseDto = new ResponsePayFacadeDto();
        responseDto.setPayDto(payService.pay(requestDto));
        responseDto.setStatus(ShippingStatus.PREPARING);
        return new ResponsePayFacadeDto();
    }

}
