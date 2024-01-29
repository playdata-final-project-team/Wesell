package com.wesell.userservice.service.feign;

import com.wesell.userservice.dto.feigndto.AuthUserInfoRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name="AUTHENTICATION-SERVER")
public interface AuthServerFeignClient {

    @GetMapping("api/v2/feign/auth-list")
    List<AuthUserInfoRequestDto> getListAuthUserInfo();

    @GetMapping("api/v2/{uuid}/email")
    String getEmail(@PathVariable("uuid") String uuid);
}