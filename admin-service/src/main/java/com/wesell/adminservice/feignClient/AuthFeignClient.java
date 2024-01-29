package com.wesell.adminservice.feignClient;

import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "AUTHENTICATION-SERVER", path = "api/v1")
public interface AuthFeignClient {

    @PutMapping("change-role")
    void changeUserRole (@RequestBody ChangeRoleRequestDto changeRoleRequestDto);

    @PutMapping("updateIsForced/{uuid}")
    void updateIsForced(@PathVariable String uuid);
}
