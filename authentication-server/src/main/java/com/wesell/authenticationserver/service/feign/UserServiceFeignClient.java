package com.wesell.authenticationserver.service.feign;

import com.wesell.authenticationserver.dto.response.CreateUserFeignResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="user-service", path="user-service")
public interface UserServiceFeignClient {

    @PostMapping("api/sign-up")
    void registerUserDetailInfo(@RequestBody CreateUserFeignResponseDto dto);

}
