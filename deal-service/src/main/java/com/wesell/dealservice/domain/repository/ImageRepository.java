package com.wesell.dealservice.domain.repository;

import com.wesell.dealservice.domain.entity.DealPost;
import com.wesell.dealservice.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findImageByPostId(Long postId);
}
