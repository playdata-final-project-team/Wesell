package com.wesell.authenticationserver.domain.service;

import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.domain.enum_.RefreshStatus;

public interface TokenService {

    // 토큰 발급
    GeneratedTokenDto generateTokens(String uuid, String role);
    // 토큰 갱신
    GeneratedTokenDto refreshToken(String refreshToken, String accessToken);
    // 토큰 검증
    RefreshStatus validateToken(String refreshToken, String accessToken);

}
