package com.wesell.dealservice.controller.feign;

import com.wesell.dealservice.service.feign.PayDealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class PayDealController {
    private final PayDealService payDealService;
    public PayDealController(PayDealService payDealService) {
        this.payDealService = payDealService;
    }

    @GetMapping("pay/payInfo/{postId}")
    public ResponseEntity<Long> getPrice(@RequestParam("postId") Long postId) {
        return ResponseEntity.ok(payDealService.getPrice(postId));
    }
}
