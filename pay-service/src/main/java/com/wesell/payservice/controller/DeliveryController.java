package com.wesell.payservice.controller;

import com.wesell.payservice.domain.dto.request.DeliveryRequestDto;
import com.wesell.payservice.domain.dto.request.DeliveryUpdateRequestDto;
import com.wesell.payservice.service.delivery.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2")
public class DeliveryController {
    private final DeliveryService deliveryService;
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("delivery")
    public ResponseEntity<Long> delivery (@RequestBody DeliveryRequestDto requestDto) {
        return new ResponseEntity<>(deliveryService.createDelivery(requestDto), HttpStatus.CREATED);
    }

    @PutMapping("delivery/update")
    public ResponseEntity<?> update (@RequestBody DeliveryUpdateRequestDto requestDto){
        return new ResponseEntity<>(deliveryService.updateDeliveryInfo(requestDto), HttpStatus.OK);
    }

    @PutMapping("delivery/start")
    public ResponseEntity<?> startDelivery(@RequestParam("id") Long deliveryId, @RequestBody Integer shippingNumber){
        deliveryService.startDelivery(shippingNumber, deliveryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("delivery/finish")
    public ResponseEntity<?> finishDelivery(@RequestParam("id") Long deliveryId){
        deliveryService.finishDelivery(deliveryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
