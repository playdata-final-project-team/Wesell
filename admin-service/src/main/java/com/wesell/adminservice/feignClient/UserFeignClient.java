package com.wesell.adminservice.feignClient;

import com.wesell.adminservice.dto.response.AdminUserResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name="USER-SERVICE")
public interface UserFeignClient {

    @GetMapping("users")
    ResponseEntity<List<UserListResponseDto>> getUserList();

    @GetMapping("search-users")
    List<AdminUserResponseDto> searchUsers(
            @RequestParam("name") String name,
            @RequestParam("nickname") String nickname,
            @RequestParam("phone") String phone,
            @RequestParam("uuid") String uuid
    );
}
