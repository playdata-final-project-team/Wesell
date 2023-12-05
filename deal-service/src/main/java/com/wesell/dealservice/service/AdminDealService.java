package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.AdminDealRepository;
import com.wesell.dealservice.domain.repository.DealRepository;
import com.wesell.dealservice.dto.response.MyPostListResponseDto;
import com.wesell.dealservice.error.ErrorCode;
import com.wesell.dealservice.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminDealService {
    private final AdminDealRepository adminDealRepository;
    private final DealRepository dealRepository;

    public Page<MyPostListResponseDto> getPostList(String uuid, int page, int size) {
        checkValidationByUuid(uuid);
        Page<DealPost> pageResult = adminDealRepository.findAllByUuidAndIsDeleted(uuid, false, PageRequest.of(page, size));
        return pageResult.map(MyPostListResponseDto::new);
    }

    public void checkValidationByUuid(String uuid) {
        DealPost post = dealRepository.findFirstByUuid(uuid);
        if (post == null || !uuid.equals(post.getUuid())) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }
}
