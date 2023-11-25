package com.wesell.dealservice.service;

import com.wesell.dealservice.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.dto.request.EditPostRequestDto;
import com.wesell.dealservice.dto.response.EditPostResponseDto;
import com.wesell.dealservice.dto.response.MainPagePostResponseDto;
import com.wesell.dealservice.dto.response.MyPostListResponseDto;
import com.wesell.dealservice.dto.response.PostInfoResponseDto;
import java.util.List;

public interface DealService {
    void createDealPost(UploadDealPostRequestDto requestCreatePostDto);
    EditPostResponseDto editPost(EditPostRequestDto requestDto, Long postId);
    void deletePost(String uuid, Long postId);
    PostInfoResponseDto getPostInfo(Long postId);
    List<MyPostListResponseDto> getMyPostList(String uuid);
    List<MainPagePostResponseDto> getDealPostLists();
    void changePostStatus(String uuid, Long id);
}