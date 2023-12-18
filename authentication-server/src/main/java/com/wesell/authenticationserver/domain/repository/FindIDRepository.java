package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FindIDRepository extends JpaRepository<AuthUser, Long> {

    @Query("SELECT a.email from AuthUser a WHERE a.uuid = :uuid")
    Optional<String> findEmailByUuid(@Param("uuid") String uuid);
}
