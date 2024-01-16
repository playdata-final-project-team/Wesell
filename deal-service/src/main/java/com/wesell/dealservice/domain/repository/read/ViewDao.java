package com.wesell.dealservice.domain.repository.read;

import com.wesell.dealservice.domain.entity.DealPost;
import feign.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface ViewDao extends Repository<DealPost, Long> {
    @Query("SELECT dp FROM DealPost dp "
            + "WHERE dp.id =:productId")
    DealPost searchById(Long productId);

    @Query("SELECT dp.price FROM DealPost dp "
            + "WHERE dp.id =:productId")
    Long searchPriceById(@Param("productId") Long productId);
}
