package com.wesell.adminservice.feignClient;

import com.wesell.adminservice.domain.dto.UserListResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="USER-SERVICE", path = "user-service")
public interface UserFeignClient {

    @GetMapping("user-list")
    UserListResponseDto getUserList();
}
