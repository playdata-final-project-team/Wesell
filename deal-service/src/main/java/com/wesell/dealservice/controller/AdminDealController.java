package com.wesell.dealservice.controller;

import com.wesell.dealservice.dto.response.MyPostListResponseDto;
import com.wesell.dealservice.service.AdminDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminDealController {
    private final AdminDealService adminDealService;

    @GetMapping("admin/list")
    Page<MyPostListResponseDto> getPostList(@RequestParam("uuid") String uuid,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size){
        return adminDealService.getPostList(uuid, page, size);
    }
}
