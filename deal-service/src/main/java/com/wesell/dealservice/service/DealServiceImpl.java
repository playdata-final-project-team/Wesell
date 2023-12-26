package com.wesell.dealservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.dealservice.domain.dto.request.ChangePostRequestDto;
import com.wesell.dealservice.domain.dto.response.*;
import com.wesell.dealservice.domain.entity.Image;
import com.wesell.dealservice.domain.repository.ImageRepository;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.CategoryRepository;
import com.wesell.dealservice.domain.repository.DealRepository;
import com.wesell.dealservice.domain.repository.read.DealPostReadRepository;
import com.wesell.dealservice.error.exception.CustomException;
import com.wesell.dealservice.feignClient.UserFeignClient;
import com.wesell.dealservice.util.Producer;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import static com.wesell.dealservice.error.ErrorCode.INVALID_POST;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class DealServiceImpl implements DealService {

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
        Category category = categoryRepository.findById(Long.parseLong(requestDto.getCategoryId())).get();
        DealPost post = DealPost.builder()
                .uuid(requestDto.getUuid())
                .category(category)
                .title(requestDto.getTitle())
                .price(Long.parseLong(requestDto.getPrice()))
                .link(requestDto.getLink())
                .detail(requestDto.getDetail())
                .createdAt(LocalDateTime.now())
                .build();
        dealRepository.save(post);

        return post.getId();
    }

    @Override
    public EditPostResponseDto editPost(EditPostRequestDto requestDto) {
        DealPost editPost = dealRepository.findDealPostById(requestDto.getPostId());
        editPost.editPost(requestDto);
        Category category = categoryRepository.findById(requestDto.getCategoryId()).get();
        editPost.editCategory(category);

        return new EditPostResponseDto(editPost);
    }

    @Override
    public void deletePost(Long postId) {
        DealPost post = dealRepository.findDealPostById(postId);
        post.deleteMyPost();
    }

    @Override
    public void changePostStatus(ChangePostRequestDto requestDto) {
        dealRepository.findDealPostByIdAndIsDeleted(requestDto.getPostId(), false).changeStatus();
    }

    @Override
    public PostInfoResponseDto getPostInfo(Long postId) {
        DealPost foundPost = readRepository.searchDealPost(postId);
        String nickname = userFeignClient.getNicknameByUuid(foundPost.getUuid());
        String imageUrl = imageRepository.findImageByPostId(foundPost.getId()).getImageUrl();
        return new PostInfoResponseDto(foundPost, nickname, imageUrl);
    }

    @Override
    @Cacheable(key = "'uuid: ' + #uuid + ' page: ' + #page", value = "MYPAGE_CACHE")
    public PageResponseDto getMyPostList(String uuid, int page) {
        int pageLimit = 6;
        Page<DealPost> allByUuid = readRepository.searchMyList(uuid, PageRequest.of(page, pageLimit));
        return PageResponseDto.builder()
                .dtoList(allByUuid.map(MyPostListResponseDto::new).stream().toList())
                .page(page)
                .totalPage(pageLimit)
                .build();
    }

    @Override
    @Cacheable(key = "'page: ' + #page", value = "MAIN_CACHE")
    public PageResponseDto getDealPostLists(int page) {
        int pageLimit = 8;
        Page<DealPost> posts = readRepository.searchDealPostList(PageRequest.of(page, pageLimit));
        log.info(posts.toList());
        return PageResponseDto.builder()
                .dtoList(posts.map(post -> new MainPagePostResponseDto(post, imageRepository.findImageByPostId(post.getId()))).toList())
                .page(page)
                .totalPage(pageLimit)
                .build();
    }


    @Override
    public Page<MainPagePostResponseDto> findByCategory(Long categoryId, int page) {
        int pageLimit = 8;
        Page<DealPost> posts = readRepository.searchByCategory(categoryId, PageRequest.of(page, pageLimit));
        return posts.map(post -> {
            Image image = imageRepository.findImageByPostId(post.getId());
            return new MainPagePostResponseDto(post, image);
        });
    }

    @Override
    public Page<MainPagePostResponseDto> findByTitle(String title , int page) {
        int pageLimit = 8;
        Page<DealPost> posts = readRepository.searchByTitle(title, PageRequest.of(page, pageLimit));
        return posts.map(post -> {
            Image image = imageRepository.findImageByPostId(post.getId());
            return new MainPagePostResponseDto(post, image);
        });
    }

    public <T> void publishCreateItemMessage(T dto) throws JsonProcessingException {

        // DTO를 json으로 직렬화
        String message = objectMapper.writeValueAsString(dto);

        producer.sendMessage(message);
    }

    public EditPostResponseDto CompareWithUpdated(EditPostRequestDto dto) {
        DealPost editPost = dealRepository.findDealPostById(dto.getPostId());
        if(editPost.getTitle().equals(dto.getTitle()) && editPost.getPrice() == dto.getPrice()
                && editPost.getLink().equals(dto.getLink()) && editPost.getDetail().equals(dto.getDetail())) {
            return new EditPostResponseDto(editPost);
        }
        else
            throw new CustomException(INVALID_POST);
    }

}