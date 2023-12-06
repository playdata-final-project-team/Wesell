package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface FindPWDRepository extends JpaRepository<AuthUser, Long> {

    @Query("select a.uuid from AuthUser a where a.email = :email")
    Optional<String> findUuidByEmail(String email);

    Optional<AuthUser> findByUuid(String uuid);
}
