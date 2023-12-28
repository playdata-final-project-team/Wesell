package com.wesell.authenticationserver.service.feign;

import com.wesell.authenticationserver.controller.response.ResponseDto;
import com.wesell.authenticationserver.service.dto.response.CreateUserFeignResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE", url = "18.218.24.186:8082", path = "api/v1")
public interface UserServiceFeignClient {

    @PostMapping("sign-up")
    ResponseEntity<ResponseDto> registerUserDetailInfo(@RequestBody CreateUserFeignResponseDto dto);

    @PostMapping("feign/find/id")
    String findID(@RequestBody String phoneNumber);
}
