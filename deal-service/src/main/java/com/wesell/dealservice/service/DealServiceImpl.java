package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.dto.request.CreateDealPostRequestDto;
import com.wesell.dealservice.dto.request.EditPostRequestDto;
import com.wesell.dealservice.dto.response.*;
import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.CategoryRepository;
import com.wesell.dealservice.domain.repository.DealRepository;
import com.wesell.dealservice.error.ErrorCode;
import com.wesell.dealservice.error.exception.CustomException;
import com.wesell.dealservice.feignClient.UserFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final CategoryRepository categoryRepository;
    private final UserFeignClient userFeignClient;

    // 거래 글 생성
    @Override
    public void createDealPost(CreateDealPostRequestDto requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId()).get();
        DealPost post = DealPost.builder()
                .uuid(requestDto.getUuid())
                .category(category)
                .title(requestDto.getTitle())
                .price(requestDto.getPrice())
                .link(requestDto.getLink())
                .detail(requestDto.getDetail())
                .createdAt(LocalDate.now())
                .build();
        dealRepository.save(post);
    }

    // 거래 글 수정
    @Override
    public EditPostResponseDto editPost(EditPostRequestDto requestDto, Long postId) {
        checkValidationByUuid(requestDto.getUuid());

        DealPost editPost = dealRepository.findDealPostByUuidAndId(requestDto.getUuid(), postId);
        editPost.editPost(requestDto);
        Category category = categoryRepository.findById(requestDto.getCategoryId()).get();
        editPost.editCategory(category);

        return new EditPostResponseDto(editPost);
    }

    // 거래 글 삭제
    @Override
    public void deletePost(String uuid, Long postId) {
        checkValidationByUuid(uuid);
        dealRepository.deleteById(postId);
    }

    //거래 글 상세 정보
    @Override
    public PostInfoResponseDto getPostInfo(Long postId) {
        DealPost foundPost = dealRepository.findDealPostById(postId);
        String nickname = userFeignClient.getNicknameByUuid(foundPost.getUuid());
        return new PostInfoResponseDto(foundPost, nickname);
    }

    // 판매 내역 리스트 정보
    @Override
    public List<MyPostListResponseDto> getMyPostList(String uuid) {
        checkValidationByUuid(uuid);
        List<DealPost> allByUuid = dealRepository.findAllByUuid(uuid);
        return allByUuid.stream().map(MyPostListResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<MainPagePostResponseDto> getDealPostLists() {
        List<DealPost> dealPosts = dealRepository.findAllByStatus(SaleStatus.IN_PROGRESS);
        return dealPosts.stream().map(MainPagePostResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void changePostStatus(String uuid, Long id) {
        checkValidationByUuid(uuid);
        DealPost post = dealRepository.findDealPostById(id);
        post.changeStatus();
    }

    public void checkValidationByUuid(String uuid) {
        DealPost post = dealRepository.findDealPostByUuid(uuid);
        if(!uuid.equals(post.getUuid())) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }

}