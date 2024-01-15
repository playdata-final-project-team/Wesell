package com.wesell.payservice.controller;

import com.wesell.payservice.domain.dto.request.RequestPayDto;
import com.wesell.payservice.domain.dto.response.ResponseDetailFacadeDto;
import com.wesell.payservice.service.facade.FacadeService;
import com.wesell.payservice.service.pay.PayServiceImpl;
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

    private final PayServiceImpl payService;
    private final FacadeService facadeservice;
    public PaymentController(PayServiceImpl payService, FacadeService facadeService) {
        this.payService = payService;
        this.facadeservice = facadeService;
    }

    @PostMapping("payment")
    public ResponseEntity<Long> pay (@RequestBody RequestPayDto requestDto) {
        return new ResponseEntity<>(payService.createPay(requestDto), HttpStatus.OK);
    }

    @GetMapping("payment")
    public ResponseEntity<ResponseDetailFacadeDto> getPayResult (@RequestParam(name = "payId") Long payId) {
        return new ResponseEntity<>(facadeservice.getPayResult(payId), HttpStatus.OK);
    }
}