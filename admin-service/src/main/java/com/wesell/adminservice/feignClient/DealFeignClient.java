package com.wesell.adminservice.feignClient;

import com.wesell.adminservice.domain.dto.response.PostListResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "DEAL-SERVICE", path = "deal-service")
public interface DealFeignClient {

    @GetMapping("list")
    ResponseEntity<List<PostListResponseDto>> getPostList(@RequestParam("uuid") String uuid);

    @PutMapping("delete")
    ResponseEntity<?> deletePost(@RequestParam("uuid") String uuid, @RequestParam("id") Long postId);
}
