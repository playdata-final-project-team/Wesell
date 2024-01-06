package com.wesell.authenticationserver.domain.service;

import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.enum_.RefreshStatus;
import com.wesell.authenticationserver.service.TokenServiceImpl;

public interface TokenService {

    // 토큰 발급
    public GeneratedTokenDto generateTokens(AuthUser authUser);
    // 토큰 갱신
    public GeneratedTokenDto refreshToken(String refreshToken, String accessToken);
    // 토큰 검증
    public RefreshStatus validateToken(String refreshToken, String accessToken);

}
