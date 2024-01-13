package com.wesell.authenticationserver.domain.feign;

import com.wesell.authenticationserver.service.dto.response.CreateUserFeignResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient {

    @PostMapping("api/v1/sign-up")
    void registerUserDetailInfo(@RequestBody CreateUserFeignResponseDto dto);

    @PostMapping("api/v1/users/phone/uuid")
    String findID(@RequestBody String phoneNumber);

    @DeleteMapping("api/v1/users/{uuid}")
    ResponseEntity<Void> deleteUser(@PathVariable(value="uuid") String uuid);

}
