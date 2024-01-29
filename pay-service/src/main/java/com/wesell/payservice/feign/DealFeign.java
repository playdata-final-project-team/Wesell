package com.wesell.payservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Request to deal-service
@FeignClient(name = "deal-service", path = "api/v2")
public interface DealFeign {
    @GetMapping("payments/{productId}/amount")
    Long getPayInfo(@PathVariable(value = "productId") Long productId);

    @GetMapping("titles/{productId}/title")
    String getTitle(@PathVariable(value = "productId") Long productId);
}
