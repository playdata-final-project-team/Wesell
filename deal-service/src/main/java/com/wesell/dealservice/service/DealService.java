package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.ChangePostRequestDto;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.response.*;
import org.springframework.data.domain.Page;

public interface DealService {
    Long createDealPost(UploadDealPostRequestDto requestCreatePostDto);
    EditPostResponseDto editPost(EditPostRequestDto requestDto);
    void deletePost(Long postId);
    PostInfoResponseDto getPostInfo(Long postId);
    PageResponseDto getMyPostList(String uuid, int page);
    PageResponseDto getDealPostLists(int page);
    void changePostStatus(ChangePostRequestDto requestDto);
    Page<MainPagePostResponseDto> findByCategory(Long categoryId, int page);
    Page<MainPagePostResponseDto> findByTitle(String title, int page);
}