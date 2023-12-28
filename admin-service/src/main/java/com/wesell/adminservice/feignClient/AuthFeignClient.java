package com.wesell.adminservice.feignClient;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "AUTHENTICATION-SERVER", url = "3.17.154.72:8081",path = "api/v1")
public interface AuthFeignClient {

    @PutMapping("change-role")
    ResponseEntity<String> changeUserRole (@RequestBody ChangeRoleRequestDto changeRoleRequestDto);

    @PutMapping("updateIsForced/{uuid}")
    ResponseEntity<String> updateIsForced(@PathVariable String uuid);
}
