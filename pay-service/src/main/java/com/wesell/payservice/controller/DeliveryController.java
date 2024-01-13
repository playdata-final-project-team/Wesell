package com.wesell.payservice.controller;

import com.wesell.payservice.domain.dto.request.RequestDeliveryDto;
import com.wesell.payservice.service.delivery.DeliveryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2")
public class DeliveryController {
    private final DeliveryServiceImpl deliveryService;
    public DeliveryController(DeliveryServiceImpl deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("delivery")
    public ResponseEntity<Long> delivery (@RequestBody RequestDeliveryDto requestDto) {
        return new ResponseEntity<>(deliveryService.createDelivery(requestDto), HttpStatus.CREATED);
    }
}
