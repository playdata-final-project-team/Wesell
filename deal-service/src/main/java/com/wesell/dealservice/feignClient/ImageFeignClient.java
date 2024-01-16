package com.wesell.dealservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "image-server")
public interface ImageFeignClient {

    @GetMapping("api/v2/images/{productId}/url")
    String getUrlByProductId(@PathVariable(value = "productId") Long postId);
}
