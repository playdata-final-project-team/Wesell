package com.wesell.dealservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Request to user-service
@FeignClient(name = "USER-SERVICE", path = "user-service")
public interface UserFeignClient {

    @GetMapping("/users/{uuid}")
    String getNicknameByUuid(@PathVariable String uuid);

}