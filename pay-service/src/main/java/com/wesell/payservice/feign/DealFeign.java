package com.wesell.payservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Request to deal-service
@FeignClient(name = "deal-service")
public interface DealFeign {
    @GetMapping("api/v2/payments/{productId}/amount")
    Long getPayInfo(@PathVariable(value = "productId") Long productId);

}
