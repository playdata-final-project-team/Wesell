package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
    Optional<AuthUser> findByUuid(String uuid);
}
