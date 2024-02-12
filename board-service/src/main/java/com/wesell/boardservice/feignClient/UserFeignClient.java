package com.wesell.boardservice.feignClient;

import com.wesell.boardservice.domain.dto.feign.BoardFeignRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @PostMapping("api/v2/FindNickName")
    String findNicknameByUuid(@RequestBody String uuid);

}
