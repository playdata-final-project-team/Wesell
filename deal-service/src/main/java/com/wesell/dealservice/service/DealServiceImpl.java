package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.CreateDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.response.EditPostResponseDto;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.DealRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
    public EditPostResponseDto editPost(EditPostRequestDto requestDto, String postId) {
        DealPost editPost = dealRepository.findPostByUserUuid(requestDto.getUuid(), postId);
        editPost.editPost(requestDto);
        return new EditPostResponseDto(editPost.getCategory(), editPost.getTitle(), editPost.getPrice(), editPost.getLink(), editPost.getDetail());
    }


}
