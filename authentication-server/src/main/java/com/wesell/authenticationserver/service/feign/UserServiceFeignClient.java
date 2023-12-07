package com.wesell.authenticationserver.service.feign;

import com.wesell.authenticationserver.service.dto.response.CreateUserFeignResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE", path = "api/v1")
public interface UserServiceFeignClient {

    @PostMapping("api/signup")
    ResponseEntity<String> registerUserDetailInfo(@RequestBody CreateUserFeignResponseDto dto);

    @PostMapping("feign/find/id")
    String findID(@RequestBody String phoneNumber);
}
