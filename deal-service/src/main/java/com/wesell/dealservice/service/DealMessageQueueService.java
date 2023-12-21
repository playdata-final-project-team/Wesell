package com.wesell.dealservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.dto.request.ChangePostRequestDto;
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
import com.wesell.dealservice.domain.repository.read.DealPostReadRepository;
import com.wesell.dealservice.error.ErrorCode;
import com.wesell.dealservice.error.exception.CustomException;
import com.wesell.dealservice.feignClient.UserFeignClient;
import com.wesell.dealservice.util.Producer;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class DealMessageQueueService implements DealService {

    private final DealRepository dealRepository;
    private final DealPostReadRepository readRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final UserFeignClient userFeignClient;
    private final Producer producer;
    private final ObjectMapper objectMapper;

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
    public EditPostResponseDto editPost(EditPostRequestDto requestDto) {
        checkValidationByUuid(requestDto.getUuid());
        DealPost editPost = dealRepository.findDealPostByUuidAndId(requestDto.getUuid(), requestDto.getPostId());
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
        DealPost foundPost = readRepository.searchDealPost(postId);
        checkPostValidation(foundPost);
        String nickname = userFeignClient.getNicknameByUuid(foundPost.getUuid());
        String imageUrl = imageRepository.findImageByPostId(foundPost.getId()).getImageUrl();
        return new PostInfoResponseDto(foundPost, nickname, imageUrl);
    }

    @Override
    public Page<MyPostListResponseDto> getMyPostList(String uuid, int page) {
        checkValidationByUuid(uuid);
        int pageLimit = 6;

        Page<DealPost> allByUuid = readRepository.searchMyList(uuid, PageRequest.of(page, pageLimit));
        return allByUuid.map(MyPostListResponseDto::new);
    }

    @Override
    public Page<MainPagePostResponseDto> getDealPostLists(int page) {
        int pageLimit = 8;
        Page<DealPost> dealPosts = readRepository.searchDealPostList(PageRequest.of(page, pageLimit));
        return dealPosts.map(MainPagePostResponseDto::new);
    }

    @Override
    public void changePostStatus(ChangePostRequestDto requestDto) {
        checkValidationByUuid(requestDto.getUuid());
        dealRepository.findDealPostByIdAndIsDeleted(requestDto.getPostId(), false).changeStatus();
    }

    @Override
    public Page<MainPagePostResponseDto> findByCategory(Long categoryId, int page) {
        int pageLimit = 8;
        Page<DealPost> posts = readRepository.searchByCategory(categoryId, PageRequest.of(page, pageLimit));
        return posts.map(MainPagePostResponseDto::new);
    }

    @Override
    public Page<MainPagePostResponseDto> findByTitle(String title, int page) {
        int pageLimit = 8;
        Page<DealPost> posts = readRepository.searchByTitle(title, PageRequest.of(page, pageLimit));
        return posts.map(MainPagePostResponseDto::new);
    }

    @Override
    public Page<MainPagePostResponseDto> findByCategoryAndTitle(Long categoryId, String title, int page) {
        int pageLimit = 8;
        Page<DealPost> posts = readRepository.searchByCategoryAndTitle(categoryId, title, PageRequest.of(page, pageLimit));
        return posts.map(MainPagePostResponseDto::new);
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

    public <T> void publishCreateItemMessage(T dto) throws JsonProcessingException {

        // DTO를 json으로 직렬화
        String message = objectMapper.writeValueAsString(dto);
        producer.sendMessage(message);
    }

    public <T> void publishUpdateItemMessage(T dto) throws JsonProcessingException {

        String message = objectMapper.writeValueAsString(dto);
        producer.updateMessage(message);
    }

}