package com.wesell.dealservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Request to user-service
@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("api/v1/users{uuid}/nickname")
    String getNicknameByUuid(@PathVariable(value = "uuid") String uuid);

}