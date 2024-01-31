package com.wesell.payservice.domain.repository;

import com.wesell.payservice.domain.entity.Delivery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OptimisticRepository {
    @PersistenceContext
    private EntityManager manager;

    public Delivery findByIdOptimisticLockMode(Long id) {
        return manager.find(
                Delivery.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }
}
