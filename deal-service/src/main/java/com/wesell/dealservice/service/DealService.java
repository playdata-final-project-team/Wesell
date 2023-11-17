package com.wesell.dealservice.service;

import com.wesell.dealservice.dto.request.CreateDealPostRequestDto;
import com.wesell.dealservice.dto.request.EditPostRequestDto;
import com.wesell.dealservice.dto.response.EditPostResponseDto;
import com.wesell.dealservice.dto.response.MainPagePostResponseDto;
import com.wesell.dealservice.dto.response.MyPostListResponseDto;
import com.wesell.dealservice.dto.response.PostInfoResponseDto;
import java.util.List;

public interface DealService {
    void createDealPost(CreateDealPostRequestDto requestCreatePostDto);
    EditPostResponseDto editPost(EditPostRequestDto requestDto, Long postId);
    void deletePost(Long postId);
    PostInfoResponseDto getPostInfo(Long postId);
    List<MyPostListResponseDto> getMyPostList(String uuid);
    List<MainPagePostResponseDto> getMainPageInfo();
    void changePostStatus(Long id);
}
