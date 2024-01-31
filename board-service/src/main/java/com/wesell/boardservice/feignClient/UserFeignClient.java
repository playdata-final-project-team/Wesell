package com.wesell.boardservice.feignClient;

import com.wesell.boardservice.domain.dto.feign.BoardFeignRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @PostMapping("api/v1/FindNickName")
    String findNicknameByUuid(String uuid);

    @PostMapping("api/v1/getNickName")
    String getNicknameByUuid(String uuid);
}
