package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.RequestCreateDealPostDto;
import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.DealRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository salesPostRepository;

    @Override
    public void createDealPost(RequestCreateDealPostDto requestCreatePostDto) {
        DealPost post = requestCreatePostDto.toEntity();
        salesPostRepository.save(post);
    }

}
