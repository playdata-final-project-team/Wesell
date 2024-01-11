package com.wesell.payservice.controller;

import com.wesell.payservice.domain.dto.request.RequestPayDto;
import com.wesell.payservice.domain.dto.response.ResponsePayFacadeDto;
import com.wesell.payservice.service.facade.FacadeService;
import com.wesell.payservice.service.implement_.PayServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<ResponsePayFacadeDto> pay (@RequestBody RequestPayDto requestDto) {
        return new ResponseEntity<>(facadeservice.pay(requestDto), HttpStatus.OK);
    }

}