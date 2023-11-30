package com.wesell.dealservice.controller.usercontroller;


import com.wesell.dealservice.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NicknameController {
    private final UserFeignClient userFeignClient;
    @GetMapping("/users/{uuid}/nickname")
    public String getNicknameByUuid(@PathVariable String uuid) {
        return userFeignClient.getNicknameByUuid(uuid);
    }
}
