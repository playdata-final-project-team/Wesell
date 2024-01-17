package com.wesell.dealservice.feignClient;

import com.wesell.dealservice.domain.dto.response.UserFeignResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Request to user-service
@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("api/v1/users/{uuid}/dealInfo")
    UserFeignResponseDto getDealInfoByUuid(@PathVariable(value = "uuid") String uuid);

}