package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TokenInfoRepository extends JpaRepository<TokenInfo,String> {
    Optional<TokenInfo> findByAccessToken(String accessToken);

    Optional<TokenInfo> findByRefreshToken(String refreshToken);
}
