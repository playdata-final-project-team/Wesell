package com.wesell.adminservice.feignClient;

import com.wesell.adminservice.dto.response.PostListResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "DEAL-SERVICE", url = "18.226.75.205:8888",path = "api/v1")
public interface DealFeignClient {

    @GetMapping("admin/list")
    Page<PostListResponseDto> getPostList(@RequestParam("uuid") String uuid,
                                          @RequestParam("page") int page,
                                          @RequestParam("size") int size);

    @PutMapping("deal-service/delete")
    ResponseEntity<?> deletePost(@RequestParam("uuid") String uuid, @RequestParam("id") Long postId);
}
