package com.wesell.dealservice.domain.repository;

import com.wesell.dealservice.domain.entity.DealPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminDealRepository extends JpaRepository<DealPost, Long> {
    Page<DealPost> findAllByUuidAndIsDeleted(String uuid, Boolean isDeleted, Pageable pageable);
}
