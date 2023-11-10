package com.wesell.adminservice.domain.repository;

import com.wesell.adminservice.domain.entity.SiteConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<SiteConfig, Long> {
}
