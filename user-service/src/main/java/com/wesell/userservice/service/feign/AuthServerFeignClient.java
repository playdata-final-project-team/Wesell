package com.wesell.userservice.service.feign;

import com.wesell.userservice.dto.feigndto.AuthUserInfoRequestDto;
import com.wesell.userservice.dto.feigndto.EmailInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name="AUTHENTICATION-SERVER", url = "3.17.154.72:8081", path = "api/v1")
public interface AuthServerFeignClient {

    @GetMapping("feign/auth-list")
    List<AuthUserInfoRequestDto> getListAuthUserInfo();

    @GetMapping("emailinfo/{uuid}")
    ResponseEntity<EmailInfoDto> getEmailInfo(@PathVariable("uuid") String uuid);


}