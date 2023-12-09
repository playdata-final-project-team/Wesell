package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface UserDeleteRepository extends JpaRepository<AuthUser, String> {
    @Query("select a from AuthUser a where a.uuid = :uuid")
    AuthUser findByUuid(String uuid);

}