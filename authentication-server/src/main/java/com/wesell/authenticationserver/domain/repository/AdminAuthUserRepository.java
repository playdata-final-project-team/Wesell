package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminAuthUserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findByUuid(String uuid);
}