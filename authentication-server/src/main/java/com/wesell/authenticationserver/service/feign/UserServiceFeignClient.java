package com.wesell.authenticationserver.service.feign;

import com.wesell.authenticationserver.controller.response.ResponseDto;
import com.wesell.authenticationserver.service.dto.response.CreateUserFeignResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient {

    @PostMapping("api/v1/sign-up")
    ResponseEntity<ResponseDto> registerUserDetailInfo(@RequestBody CreateUserFeignResponseDto dto);

    @PostMapping("api/v1/feign/find/id")
    String findID(@RequestBody String phoneNumber);
}
