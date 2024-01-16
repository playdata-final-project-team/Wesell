package com.wesell.imageserver.domain.repository;

import com.wesell.imageserver.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface ProductImageViewDao extends Repository<ProductImage, Long> {
    @Query("SELECT p.imageUrl FROM ProductImage p "
            + "WHERE p.id =:productId")
    String searchUrlByProductId(Long productId);
}
