package com.wesell.imageserver.domain.repository;

import com.wesell.imageserver.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
