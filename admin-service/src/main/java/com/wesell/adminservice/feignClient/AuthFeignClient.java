package com.wesell.adminservice.feignClient;

import com.wesell.adminservice.domain.dto.request.ChangeRoleRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="AUTHENTICATION-SERVER", path = "admin-auth-server")
public interface AuthFeignClient {

    @PutMapping("change-role/{uuid}")
    ResponseEntity<String> changeUserRole (@PathVariable String uuid, @RequestBody ChangeRoleRequestDto changeRoleRequestDto);
}
