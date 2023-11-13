package com.wesell.dealservice.domain.repository;

import com.wesell.dealservice.domain.dto.response.EditPostResponseDto;
import com.wesell.dealservice.domain.entity.DealPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<DealPost, Long> {
    DealPost findDealPostByUuidAndId(String uuid, Long id);
}
