package com.wesell.payservice.domain.dto.search;

import com.wesell.payservice.domain.entity.Pay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

//단순 조회 viewDao
public interface PayViewDao extends Repository<Pay, Long> {
    @Query("SELECT p FROM Pay p "
            + "WHERE p.id =:payId")
    Pay searchPayById(Long payId);

}
