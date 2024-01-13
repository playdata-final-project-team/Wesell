package com.wesell.payservice.service.implement_;

import com.wesell.payservice.domain.dto.request.RequestPayDto;
import com.wesell.payservice.domain.dto.response.ResponsePayDto;
import com.wesell.payservice.domain.entity.Pay;
import com.wesell.payservice.domain.repository.PayRepository;
import com.wesell.payservice.feign.DealFeign;
import com.wesell.payservice.service.interface_.PayService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class PayServiceImpl implements PayService {
    private final PayRepository payRepository;
    private final DealFeign dealFeign;
    public PayServiceImpl(PayRepository payRepository, DealFeign dealFeign) {
        this.payRepository = payRepository;
        this.dealFeign = dealFeign;
    }

    @Override
    public Long pay(RequestPayDto requestDto) {
        Pay pay = Pay.createPay(requestDto, createOrderNumber(requestDto), dealFeign.getPayInfo(requestDto.getProductId()));
        payRepository.save(pay);
        return pay.getId();
    }

    @Override
    public String createOrderNumber(RequestPayDto requestDto) {
        String format = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String productId = String.valueOf(requestDto.getProductId());
        return  format+productId;
    }

    @Override
    public ResponsePayDto getPayResult(Long payId) {
        Pay pay = payRepository.findPayByPayId(payId);
        return  new ResponsePayDto(pay);
    }

}
