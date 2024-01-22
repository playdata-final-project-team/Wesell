package com.wesell.chatservice.domain.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(name="image-service")
public interface ImageFeignService {

    @GetMapping("api/v2/image-urls")
    Map<Long,String> getUrlByProductId(@RequestBody List<Long> ids);
}
