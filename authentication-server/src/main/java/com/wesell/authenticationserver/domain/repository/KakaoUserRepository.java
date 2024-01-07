package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, String> {
    Optional<KakaoUser> findByEmail(String email);
}
