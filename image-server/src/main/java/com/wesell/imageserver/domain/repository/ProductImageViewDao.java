package com.wesell.imageserver.domain.repository;

import com.wesell.imageserver.domain.entity.ProductImage;
import org.springframework.data.repository.Repository;

public interface ProductImageViewDao extends Repository<ProductImage, Long> {
    ProductImage searchProductImageByPostId(Long postId);
}
