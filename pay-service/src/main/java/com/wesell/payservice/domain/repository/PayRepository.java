package com.wesell.payservice.domain.repository;

import com.wesell.payservice.domain.entity.Pay;
import feign.Param;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PayRepository extends JpaRepository<Pay, Long> {
    Pay findPayById(Long payId);

    @Query("SELECT p FROM Pay p "
            + "WHERE p.buyer =:buyer "
            + "ORDER BY p.createdAt DESC")
    Optional<Page<Pay>> findPayByBuyer(@Param("buyer") String buyer, Pageable pageable);

}
