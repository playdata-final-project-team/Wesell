package com.wesell.payservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "deal-service")
public interface DealFeign {
    @GetMapping("api/v1/pay/payInfo/{postId}")
    Long getPayInfo(@PathVariable(value = "postId") Long postId);

}
