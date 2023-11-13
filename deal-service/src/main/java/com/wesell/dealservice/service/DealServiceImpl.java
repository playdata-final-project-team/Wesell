package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.CreateDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.response.EditPostResponseDto;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.DealRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    @Override
    public void createDealPost(CreateDealPostRequestDto requestCreatePostDto) {
        DealPost post = requestCreatePostDto.toEntity();
        dealRepository.save(post);
    }

    @Override
    public void editPost(EditPostRequestDto requestDto, String postId) {
        DealPost editPost = dealRepository.findPostByUuid(requestDto.getUuid(), postId);
        editPost.editPost(requestDto);
        new EditPostResponseDto(editPost.getCategory(), editPost.getTitle(), editPost.getPrice(), editPost.getLink(), editPost.getDetail());
    }


}
