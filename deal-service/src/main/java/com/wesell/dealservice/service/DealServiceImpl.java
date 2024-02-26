package com.wesell.dealservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.dealservice.domain.dto.request.ChangePostRequestDto;
import com.wesell.dealservice.domain.dto.response.*;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.CategoryRepository;
import com.wesell.dealservice.domain.repository.DealRepository;
import com.wesell.dealservice.domain.repository.read.DealPostReadRepository;
import com.wesell.dealservice.global.response.error.exception.CustomException;
import com.wesell.dealservice.feignClient.ImageFeignClient;
import com.wesell.dealservice.feignClient.UserFeignClient;
import com.wesell.dealservice.global.util.Producer;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.wesell.dealservice.global.response.error.ErrorCode.INVALID_POST;

@Service
@Transactional
@Log4j2
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealPostReadRepository readRepository;
    private final CategoryRepository categoryRepository;
    private final UserFeignClient userFeignClient;
    private final ImageFeignClient imageFeignClient;
    private final Producer producer;
    private final ObjectMapper objectMapper;

    public DealServiceImpl(DealRepository dealRepository, DealPostReadRepository readRepository, CategoryRepository categoryRepository,
                            UserFeignClient userFeignClient, ImageFeignClient imageFeignClient, Producer producer, ObjectMapper objectMapper) {
        this.dealRepository = dealRepository;
        this.readRepository = readRepository;
        this.categoryRepository = categoryRepository;
        this.userFeignClient = userFeignClient;
        this.imageFeignClient = imageFeignClient;
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    //todo: 업로드 실패시?

    @Override
    public Long createDealPost(@Valid UploadDealPostRequestDto requestDto) {
        Category category = categoryRepository.findById(Long.parseLong(requestDto.getCategoryId())).get();
        DealPost post = DealPost.builder()
                .uuid(requestDto.getUuid())
                .category(category)
                .title(requestDto.getTitle())
                .price(requestDto.getPrice())
                .detail(requestDto.getDetail())
                .createdAt(LocalDateTime.now())
                .build();
        dealRepository.save(post);

        return post.getId();
    }

    @Override
    public EditPostResponseDto editPost(EditPostRequestDto requestDto) {
        DealPost editPost = dealRepository.findDealPostById(requestDto.getProductId());
        editPost.editPost(requestDto);
        Category category = categoryRepository.findById(requestDto.getCategoryId()).get();
        editPost.editCategory(category);

        return new EditPostResponseDto(editPost);
    }

    @Override
    public void deletePost(Long productId) {
        DealPost post = dealRepository.findDealPostById(productId);
        post.deleteMyPost();
    }

    public void deletePosts(Long[] idList){
        Arrays.stream(idList)
                .map(id -> dealRepository.findDealPostById(id))
                .peek(post->post.deleteMyPost())
                .forEach(post->dealRepository.saveAndFlush(post));
    }

    @Override
    public void changePostStatus(ChangePostRequestDto requestDto) {
        dealRepository.findDealPostByIdAndIsDeleted(requestDto.getProductId(), false).changeStatus();
    }

    //todo: feign 통신 에러
    @Override
    public ProductInfoResponseDto getPostInfo(Long productId) {
        DealPost foundPost = dealRepository.findDealPostById(productId);
        String nickname = userFeignClient.getDealInfoByUuid(foundPost.getUuid()).getNickname();
        Long dealCount = userFeignClient.getDealInfoByUuid(foundPost.getUuid()).getDealCount();
        String imageUrl = imageFeignClient.getUrlByProductId(productId);
        return new ProductInfoResponseDto(foundPost, nickname, dealCount,imageUrl);
    }

    @Override
    @Cacheable(key = "'uuid: ' + #uuid + ' page: ' + #page", value = "MYPAGE_CACHE")
    public PageResponseDto getMyPostList(String uuid, int page) {
        int pageLimit = 6;
        Page<DealPost> allByUuid = readRepository.searchMyList(uuid, PageRequest.of(page, pageLimit));
        return PageResponseDto.builder()
                .dtoList(allByUuid.map(MyPostListResponseDto::new).toList())
                .page(page+1)
                .totalElements(allByUuid.getTotalElements())
                .size(allByUuid.getSize())
                .build();
    }

    @Override
    @Cacheable(key = "'page: ' + #page", value = "MAIN_CACHE")
    public PageResponseDto getDealPostLists(int page) {
        int pageLimit = 8;
        Page<DealPost> posts = readRepository.searchDealPostList(PageRequest.of(page, pageLimit));
        log.info(posts.toList());
        return PageResponseDto.builder()
                .dtoList(posts.map(post -> new MainPagePostResponseDto(post, imageFeignClient.getUrlByProductId(post.getId()))).toList())
                .page(page)
                .totalElements(posts.getTotalElements())
                .size(posts.getSize())
                .build();
    }


    @Override
    public Page<MainPagePostResponseDto> findByCategory(Long categoryId, int page) {
        int pageLimit = 8;
        Page<DealPost> posts = readRepository.searchByCategory(categoryId, PageRequest.of(page, pageLimit)).orElse(null);
        return posts.map(post -> {
            return new MainPagePostResponseDto(post, imageFeignClient.getUrlByProductId(post.getId()));
        });
    }

    @Override
    public Page<MainPagePostResponseDto> findByTitle(String title , int page) {
        int pageLimit = 8;
        Page<DealPost> posts = readRepository.searchByTitle(title, PageRequest.of(page, pageLimit)).orElse(null);
        return posts.map(post -> {
            return new MainPagePostResponseDto(post, imageFeignClient.getUrlByProductId(post.getId()));
        });
    }

    public <T> void publishCreateItemMessage(T dto) throws JsonProcessingException {

        // DTO를 json으로 직렬화
        String message = objectMapper.writeValueAsString(dto);

        producer.sendMessage(message);
    }

    public EditPostResponseDto CompareWithUpdated(EditPostRequestDto dto) {
        DealPost editPost = dealRepository.findDealPostById(dto.getProductId());
        if(editPost.getTitle().equals(dto.getTitle()) && editPost.getPrice() == dto.getPrice()
                && editPost.getDetail().equals(dto.getDetail())) {
            return new EditPostResponseDto(editPost);
        }
        else
            throw new CustomException(INVALID_POST);
    }

}