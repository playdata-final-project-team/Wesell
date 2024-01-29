package com.wesell.dealservice.service.feign;

import com.wesell.dealservice.domain.dto.response.MyPostListResponseDto;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.AdminDealRepository;
import com.wesell.dealservice.domain.repository.DealRepository;
import com.wesell.dealservice.global.response.error.ErrorCode;
import com.wesell.dealservice.global.response.error.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
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
