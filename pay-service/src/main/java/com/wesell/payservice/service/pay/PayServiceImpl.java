package com.wesell.payservice.service.pay;

import com.wesell.payservice.domain.dto.request.PayRequestDto;
import com.wesell.payservice.domain.dto.response.MyPayListResponseDto;
import com.wesell.payservice.domain.entity.Delivery;
import com.wesell.payservice.domain.entity.Pay;
import com.wesell.payservice.domain.repository.DeliveryRepository;
import com.wesell.payservice.domain.repository.PayRepository;
import com.wesell.payservice.feign.DealFeign;
import com.wesell.payservice.global.response.error.ErrorCode;
import com.wesell.payservice.global.response.error.exception.CustomException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class PayServiceImpl implements PayService {
    private final PayRepository payRepository;
    private final DeliveryRepository deliveryRepository;
    private final DealFeign dealFeign;

    public PayServiceImpl(PayRepository payRepository, DeliveryRepository deliveryRepository,
                          DealFeign dealFeign) {
        this.payRepository = payRepository;
        this.deliveryRepository = deliveryRepository;
        this.dealFeign = dealFeign;
    }

    @Override
    public Long createPay(PayRequestDto requestDto) {
        //todo : amount가 null인 경우
        Long amount = Long.parseLong(dealFeign.getPayInfo(requestDto.getProductId()));
        Pay pay = Pay.createPay(requestDto, createOrderNumber(requestDto), amount);

        try{
            payRepository.save(pay);
            return pay.getId();
        } catch(OptimisticLockException e ) {
            Delivery delivery = deliveryRepository.findDeliveryById(pay.getDeliveryId());
            deliveryRepository.delete(delivery);
            throw new CustomException(ErrorCode.REJECT_REASON_SOLD_OUT);
        }
    }

    @Override
    public String createOrderNumber(PayRequestDto requestDto) {
        String format = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String productId = String.valueOf(requestDto.getProductId());
        return  format+productId;
    }

    @Override
    public Page<MyPayListResponseDto> getMyPayList(String buyer, int page) {
        int pageLimit = 8;
        Page<Pay> pays = payRepository.findPayByBuyer(buyer, PageRequest.of(page, pageLimit))
                .orElseThrow(() -> new CustomException(ErrorCode.PAY_NOT_FOUND));
        return pays.map(pay -> {
            return new MyPayListResponseDto(pay, dealFeign.getTitle(pay.getProductId()),
                    deliveryRepository.findDeliveryById(pay.getDeliveryId()));
        });
    }

    @Override
    public void deleteMyPay(Long payId) {
        Pay pay = payRepository.findPayById(payId);
        pay.deleteMyPay();
    }

    @Override
    public void deletePays(Long[] idList) {
        Arrays.stream(idList)
                .map(id -> payRepository.findPayById(id))
                .peek(pay -> pay.deleteMyPay())
                .forEach(pay -> payRepository.saveAndFlush(pay));
    }

    public Long findDeliveryByPayId(Long payId) {
        Pay pay = payRepository.findPayById(payId);
        return pay.getDeliveryId();
    }


}
