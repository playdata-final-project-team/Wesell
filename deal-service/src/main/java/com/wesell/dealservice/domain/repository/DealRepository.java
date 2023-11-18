package com.wesell.dealservice.domain.repository;

import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.entity.DealPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DealRepository extends JpaRepository<DealPost, Long> {
    DealPost findDealPostByUuidAndId(String uuid, Long id);
    DealPost findDealPostById(Long id);
    DealPost findDealPostByIdAndIsDeleted(Long id, Boolean isDeleted);
    DealPost findDealPostByUuid(String uuid);
    List<DealPost> findAllByUuidAndIsDeleted(String uuid, Boolean isDeleted);
    List<DealPost> findAllByStatusAndIsDeleted(SaleStatus status, Boolean isDeleted);
}