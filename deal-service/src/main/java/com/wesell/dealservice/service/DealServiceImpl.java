package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.dto.response.EditPostResponseDto;
import com.wesell.dealservice.domain.dto.response.MainPagePostResponseDto;
import com.wesell.dealservice.domain.dto.response.MyPostListResponseDto;
import com.wesell.dealservice.domain.dto.response.PostInfoResponseDto;
import com.wesell.dealservice.domain.repository.ImageRepository;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.CategoryRepository;
import com.wesell.dealservice.domain.repository.DealRepository;
import com.wesell.dealservice.error.ErrorCode;
import com.wesell.dealservice.error.exception.CustomException;
import com.wesell.dealservice.feignClient.UserFeignClient;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final UserFeignClient userFeignClient;

    //todo: 업로드 실패시?

    @Override
    public Long createDealPost(@Valid UploadDealPostRequestDto requestDto) {
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

        return post.getId();
    }

    @Override
    public EditPostResponseDto editPost(EditPostRequestDto requestDto, Long postId) {
        checkValidationByUuid(requestDto.getUuid());
        DealPost editPost = dealRepository.findDealPostByUuidAndId(requestDto.getUuid(), postId);
        editPost.editPost(requestDto);
        Category category = categoryRepository.findById(requestDto.getCategoryId()).get();
        editPost.editCategory(category);

        return new EditPostResponseDto(editPost);
    }

    @Override
    public void deletePost(String uuid, Long postId) {
        checkValidationByUuid(uuid);
        DealPost post = dealRepository.findDealPostByUuidAndId(uuid, postId);
        post.deleteMyPost();
    }

    @Override
    public PostInfoResponseDto getPostInfo(Long postId) {
        DealPost foundPost = dealRepository.findDealPostByIdAndStatusAndIsDeleted(postId, SaleStatus.IN_PROGRESS,  false);
        checkPostValidation(foundPost);
        String nickname = userFeignClient.getNicknameByUuid(foundPost.getUuid());
        String imageUrl = imageRepository.findImageByPostId(foundPost.getId()).getImageUrl();
        return new PostInfoResponseDto(foundPost, nickname, imageUrl);
    }

    @Override
    public List<MyPostListResponseDto> getMyPostList(String uuid) {
        checkValidationByUuid(uuid);
        List<DealPost> allByUuid = dealRepository.findAllByUuidAndIsDeleted(uuid, false);
        return allByUuid.stream().map(MyPostListResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<MainPagePostResponseDto> getDealPostLists() {
        List<DealPost> dealPosts = dealRepository.findAllByStatusAndIsDeleted(SaleStatus.IN_PROGRESS, false);
        return dealPosts.stream().map(MainPagePostResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void changePostStatus(String uuid, Long id) {
        checkValidationByUuid(uuid);
        dealRepository.findDealPostByIdAndIsDeleted(id, false).changeStatus();
    }

    @Override
    public List<DealPost> findByCategory(Category category) {
        List<DealPost> posts = dealRepository.findAllByStatusAndCategory(SaleStatus.IN_PROGRESS, category);
        return Optional.of(posts)
                .filter(post -> !post.isEmpty())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public List<DealPost> findByTitle(String title) {
        List<DealPost> posts = dealRepository.findAllByStatusAndTitle(SaleStatus.IN_PROGRESS, title);
        return Optional.of(posts)
                .filter(post -> !post.isEmpty())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public List<DealPost> findByCategoryAndTitle(Category category, String title) {
        List<DealPost> posts = dealRepository.findAllByStatusAndCategoryAndTitle(SaleStatus.IN_PROGRESS, category, title);
        return Optional.of(posts)
                .filter(post -> !post.isEmpty())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public void checkValidationByUuid(String uuid) {
        DealPost post = dealRepository.findFirstByUuid(uuid);
        if(!uuid.equals(post.getUuid())) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }

    public void checkPostValidation(DealPost post) {
        if(post.getIsDeleted() || post.getStatus().equals(SaleStatus.COMPLETED)) {
            throw new CustomException(ErrorCode.INVALID_POST);
        }
    }

}