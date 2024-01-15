package com.wesell.payservice.domain.dto.search;

import com.wesell.payservice.domain.entity.Pay;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

//단순 조회 viewDao
public interface PayViewDao extends Repository<Pay, Long> {
    @Query("SELECT p FROM Pay p "
            + "WHERE p.id =:payId")
    Pay searchPayById(Long payId);

    @Query("SELECT p.id FROM Pay p "
            + "WHERE p.buyer =:uuid")
    Page<Long> searchIdByBuyer(String uuid);

    @Query("SELECT p.createdAt FROM Pay p "
            + "WHERE p.id =:payId")
    Page<LocalDate> searchCreatedAtById(Long payId);
}
