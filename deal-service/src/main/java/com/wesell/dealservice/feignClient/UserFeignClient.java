package com.wesell.dealservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Request to user-service
@FeignClient(name = "USER-SERVICE", url = "18.218.24.186:8082", path = "api/v1")
public interface UserFeignClient {

    @GetMapping("/users/{uuid}/nickname")
    String getNicknameByUuid(@PathVariable(value = "uuid") String uuid);

}