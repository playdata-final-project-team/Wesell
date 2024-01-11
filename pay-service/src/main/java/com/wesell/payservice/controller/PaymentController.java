package com.wesell.payservice.controller;

import com.wesell.payservice.domain.dto.request.RequestPayDto;
import com.wesell.payservice.domain.dto.response.ResponsePayDto;
import com.wesell.payservice.service.implement_.PayServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2")
public class PaymentController {

    private final PayServiceImpl payService;

    @PostMapping("payment")
    public ResponseEntity<ResponsePayDto> pay (@RequestBody RequestPayDto requestDto) {
        return new ResponseEntity<>(payService.pay(requestDto), HttpStatus.OK);
    }

}