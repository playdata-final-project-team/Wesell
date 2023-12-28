package com.wesell.adminservice.feignClient;

import com.wesell.adminservice.dto.response.AdminUserResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "USER-SERVICE", url = "18.218.24.186:8082",path = "api/v1")
public interface UserFeignClient {

    @GetMapping("user-list")
    Page<UserListResponseDto> getUserList(@RequestParam("page") int page,
                                          @RequestParam("size") int size);

    @GetMapping("search-users")
    Page<AdminUserResponseDto> searchUsers(
            @RequestParam("name") String name,
            @RequestParam("nickname") String nickname,
            @RequestParam("phone") String phone,
            @RequestParam("uuid") String uuid,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );
}
