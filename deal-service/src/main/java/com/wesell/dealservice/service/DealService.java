package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.ChangePostRequestDto;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.response.EditPostResponseDto;
import com.wesell.dealservice.domain.dto.response.MainPagePostResponseDto;
import com.wesell.dealservice.domain.dto.response.MyPostListResponseDto;
import com.wesell.dealservice.domain.dto.response.PostInfoResponseDto;
import org.springframework.data.domain.Page;

public interface DealService {
    Long createDealPost(UploadDealPostRequestDto requestCreatePostDto);

    EditPostResponseDto editPost(EditPostRequestDto requestDto);

    void deletePost(String uuid, Long postId);
    PostInfoResponseDto getPostInfo(Long postId);
    Page<MyPostListResponseDto> getMyPostList(String uuid, int page);
    Page<MainPagePostResponseDto> getDealPostLists(int page);

    void changePostStatus(ChangePostRequestDto requestDto);

    Page<MainPagePostResponseDto> findByCategory(Long categoryId, int page);
    Page<MainPagePostResponseDto> findByTitle(String title, int page);
    Page<MainPagePostResponseDto> findByCategoryAndTitle(Long categoryId, String title, int page);

}