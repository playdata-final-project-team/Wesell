package com.wesell.authenticationserver.domain.repository.EmailInfoRepository;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmailInfoRepository extends JpaRepository<AuthUser,Long> {

    Optional<AuthUser> findByUuid(String uuid);
}
