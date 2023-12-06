package com.wesell.dealservice.domain.repository;

import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DealRepository extends JpaRepository<DealPost, Long> {
    DealPost findFirstByUuid(String uuid);
    DealPost findDealPostByUuidAndId(String uuid, Long id);
    DealPost findDealPostByIdAndIsDeleted(Long id, Boolean isDeleted);
    DealPost findDealPostByIdAndStatusAndIsDeleted(Long id, SaleStatus status, Boolean isDeleted);
    Page<DealPost> findAllByUuidAndIsDeletedFalse(String uuid, Pageable pageable);
    Page<DealPost> findAllByStatusAndIsDeletedFalse(SaleStatus status, Pageable pageable);
    List<DealPost> findAllByStatusAndCategory(SaleStatus status, Category category);
    Page<DealPost> findAllByStatusAndTitle(SaleStatus status, String title, Pageable pageable);
    Page<DealPost> findAllByStatusAndCategoryAndTitle(SaleStatus status, Category category, String title, Pageable pageable);
}