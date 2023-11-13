package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.CreateDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.response.EditPostResponseDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface DealService {
    void createDealPost(CreateDealPostRequestDto requestCreatePostDto);
    EditPostResponseDto editPost(EditPostRequestDto requestDto, @RequestParam String postId);
}
