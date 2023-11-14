package com.wesell.dealservice.domain.repository;

import com.wesell.dealservice.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
