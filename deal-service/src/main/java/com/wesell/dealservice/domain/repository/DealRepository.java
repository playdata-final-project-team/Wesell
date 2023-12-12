package com.wesell.dealservice.domain.repository;

import com.wesell.dealservice.domain.entity.DealPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DealRepository extends JpaRepository<DealPost, Long> {
    DealPost findFirstByUuid(String uuid);
    DealPost findDealPostByUuidAndId(String uuid, Long id);
    DealPost findDealPostByIdAndIsDeleted(Long id, Boolean isDeleted);
}