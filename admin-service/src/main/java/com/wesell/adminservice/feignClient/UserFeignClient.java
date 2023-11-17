package com.wesell.adminservice.feignClient;

import com.wesell.adminservice.domain.dto.response.UserListResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name="USER-SERVICE")
public interface UserFeignClient {

    @GetMapping("users")
    ResponseEntity<List<UserListResponseDto>> getUserList();
}
