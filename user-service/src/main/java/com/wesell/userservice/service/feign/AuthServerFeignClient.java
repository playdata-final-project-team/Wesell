package com.wesell.userservice.service.feign;

import com.wesell.userservice.dto.feigndto.AuthUserInfoRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="AUTH-SERVER", path="auth-sever")
public interface AuthServerFeignClient {

    @GetMapping("feign/auth-list")
    List<AuthUserInfoRequestDto> getListAuthUserInfo();

}
