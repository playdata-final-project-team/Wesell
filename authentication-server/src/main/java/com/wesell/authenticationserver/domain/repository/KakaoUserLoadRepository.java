package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KakaoUserLoadRepository extends JpaRepository<KakaoUser, String> {

    @Query("SELECT k.email from KakaoUser k WHERE k.uuid = :uuid")
    Optional<String> findEmailByUuid(@Param("uuid") String uuid);
}
