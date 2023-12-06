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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
        String customLocalDateTimeFormat =LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        DealPost post = DealPost.builder()
                .uuid(requestDto.getUuid())
                .category(category)
                .title(requestDto.getTitle())
                .price(requestDto.getPrice())
                .link(requestDto.getLink())
                .detail(requestDto.getDetail())
                .createdAt(customLocalDateTimeFormat)
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
    public Page<MyPostListResponseDto> getMyPostList(String uuid, int page) {
        checkValidationByUuid(uuid);
        int pageLimit = 6;

        Pageable pageable1 = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<DealPost> allByUuid = dealRepository.findAllByUuidAndIsDeletedFalse(uuid, pageable1);
        return allByUuid.map(MyPostListResponseDto::new);
    }

    @Override
    public Page<MainPagePostResponseDto> getDealPostLists(int page) {
        int pageLimit = 8;

        Pageable pageable1 = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<DealPost> dealPosts = dealRepository.findAllByStatusAndIsDeletedFalse(SaleStatus.IN_PROGRESS, pageable1);
        return dealPosts.map(MainPagePostResponseDto::new);
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
    public Page<DealPost> findByTitle(String title, int page) {
        int pageLimit = 8;

        Pageable pageable1 = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<DealPost> posts = dealRepository.findAllByStatusAndTitle(SaleStatus.IN_PROGRESS, title, pageable1);

        return Optional.of(posts)
                .filter(post -> !post.isEmpty())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public Page<DealPost> findByCategoryAndTitle(Category category, String title, int page) {
        int pageLimit = 8;

        Pageable pageable1 = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<DealPost> posts = dealRepository.findAllByStatusAndCategoryAndTitle(SaleStatus.IN_PROGRESS, category, title, pageable1);
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