package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Authentication, Long> {
}
