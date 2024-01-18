package com.wesell.payservice.controller;

import com.wesell.payservice.domain.dto.request.PayRequestDto;
import com.wesell.payservice.domain.dto.response.DetailFacadeResponseDto;
import com.wesell.payservice.service.facade.FacadeService;
import com.wesell.payservice.service.pay.PayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2")
public class PaymentController {

    private final PayService payService;
    private final FacadeService facadeservice;
    public PaymentController(PayService payService, FacadeService facadeService) {
        this.payService = payService;
        this.facadeservice = facadeService;
    }

    @PostMapping("payment")
    public ResponseEntity<Long> pay (@RequestBody PayRequestDto requestDto) {
        return new ResponseEntity<>(payService.createPay(requestDto), HttpStatus.OK);
    }

    @GetMapping("payment")
    public ResponseEntity<DetailFacadeResponseDto> getPayResult (@RequestParam(name = "payId") Long payId) {
        return new ResponseEntity<>(facadeservice.getPayResult(payId), HttpStatus.OK);
    }

    @GetMapping("mypage/payment")
    public ResponseEntity<?> getMyPays (@RequestParam(name = "id") String uuid,  @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(payService.getMyPayList(uuid, page-1), HttpStatus.OK);
    }
}