package com.wesell.chatservice.domain.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name="IMAGE-SERVER")
public interface ImageFeignService {

    @GetMapping("api/v2/image-urls")
    Map<Long,String> getUrlByProductId(@RequestParam List<Long> ids);
}
