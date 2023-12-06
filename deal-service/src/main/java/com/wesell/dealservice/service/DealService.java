package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.response.EditPostResponseDto;
import com.wesell.dealservice.domain.dto.response.MainPagePostResponseDto;
import com.wesell.dealservice.domain.dto.response.MyPostListResponseDto;
import com.wesell.dealservice.domain.dto.response.PostInfoResponseDto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface DealService {
    Long createDealPost(UploadDealPostRequestDto requestCreatePostDto);
    EditPostResponseDto editPost(EditPostRequestDto requestDto, Long postId);
    void deletePost(String uuid, Long postId);
    PostInfoResponseDto getPostInfo(Long postId);
    Page<MyPostListResponseDto> getMyPostList(String uuid, int page);
    Page<MainPagePostResponseDto> getDealPostLists(int page);
    void changePostStatus(String uuid, Long id);
    List<DealPost> findByCategory(Category category);
    Page<DealPost> findByTitle(String title, int page);
    Page<DealPost> findByCategoryAndTitle(Category category, String title, int page);
}