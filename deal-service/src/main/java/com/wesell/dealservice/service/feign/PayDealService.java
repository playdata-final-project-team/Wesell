package com.wesell.dealservice.service.feign;

import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.repository.DealRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PayDealService {
    private final DealRepository dealRepository;
    public PayDealService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public Long getPrice(Long postId) {
        DealPost post = dealRepository.findDealPostById(postId);
        return post.getPrice();
    }
}
