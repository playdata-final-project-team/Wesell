package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.response.EditPostResponseDto;
import com.wesell.dealservice.domain.dto.response.MainPagePostResponseDto;
import com.wesell.dealservice.domain.dto.response.MyPostListResponseDto;
import com.wesell.dealservice.domain.dto.response.PostInfoResponseDto;
import java.util.List;

public interface DealService {
    Long createDealPost(UploadDealPostRequestDto requestCreatePostDto);
    EditPostResponseDto editPost(EditPostRequestDto requestDto, Long postId);
    void deletePost(String uuid, Long postId);
    PostInfoResponseDto getPostInfo(Long postId);
    List<MyPostListResponseDto> getMyPostList(String uuid);
    List<MainPagePostResponseDto> getDealPostLists();
    void changePostStatus(String uuid, Long id);
    List<DealPost> findByCategory(Category category);
    List<DealPost> findByTitle(String title);
    List<DealPost> findByCategoryAndTitle(Category category, String title);
}