package com.wesell.payservice.domain.repository;

import com.wesell.payservice.domain.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, Long> {
    Pay findPayById(Long payId);
}
