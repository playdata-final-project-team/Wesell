package com.wesell.dealservice.domain.repository;

import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.entity.DealPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DealRepository extends JpaRepository<DealPost, Long> {
    DealPost findDealPostByUuidAndId(String uuid, Long id);
    DealPost findDealPostById(Long id);
    List<DealPost> findAllByUuid(String uuid);
    List<DealPost> findAllByStatus(SaleStatus status);
}