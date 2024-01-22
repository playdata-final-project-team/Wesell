package com.wesell.imageserver.domain.repository;

import com.wesell.imageserver.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatImageViewDao extends Repository<ProductImage,Long> {

    @Query("SELECT pi.productId,pi.imageUrl FROM ProductImage pi WHERE pi.productId IN :ids")
    List<Object[]> getUrlListByproductIds(@Param("ids") List<Long> ids);
}
