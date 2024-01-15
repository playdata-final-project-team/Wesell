package com.wesell.payservice.domain.repository;

import com.wesell.payservice.domain.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findDeliveryById(Long id);
}
