package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthUserLoadRepository extends JpaRepository<AuthUser, String> {

    @Query("select a.uuid from AuthUser a where a.email = :email")
    Optional<String> findUuidByEmail(@Param("email") String email);

    @Query("SELECT a.email from AuthUser a WHERE a.uuid = :uuid")
    Optional<String> findEmailByUuid(@Param("uuid") String uuid);
}
