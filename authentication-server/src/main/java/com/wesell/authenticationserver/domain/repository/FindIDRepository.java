package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FindIDRepository extends JpaRepository<AuthUser, Long> {

    @Query(value="SELECT a.email from AuthUser a WHERE a.uuid = :uuid")
    Optional<String> findEmailByUuid(String uuid);
}
