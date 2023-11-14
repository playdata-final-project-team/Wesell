package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.domain.entity.TokenInfo;
import com.wesell.authenticationserver.domain.repository.TokenInfoRepository;
import com.wesell.authenticationserver.dto.GeneratedTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenInfoService {

    private final TokenInfoRepository tokenInfoRepository;

    // 토큰 정보 저장
    @Transactional
    public void saveTokenInfo(GeneratedTokenDto dto){

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUuid(dto.getUuid());
        tokenInfo.setAccessToken(dto.getAccessToken());
        tokenInfo.setRefreshToken(dto.getRefreshToken());

        tokenInfoRepository.saveAndFlush(tokenInfo);
    }

    // 토큰 정보 수정
    @Transactional
    public void updateTokenInfo(String uuid, String accessToken){
        TokenInfo tokenInfo = tokenInfoRepository.findById(uuid)
                // 커스텀 예외 구현 예정
                .orElseThrow(() -> new RuntimeException(("TokenInfo가 존재 하지 않습니다.")));
        tokenInfo.setAccessToken(accessToken);
        tokenInfoRepository.saveAndFlush(tokenInfo);
    }

    // 토큰 정보 삭제
    @Transactional
    public void removeTokenInfo(String uuid){
        tokenInfoRepository.deleteById(uuid);
    }

    // 토큰 정보 조회 - uuid
    public Optional<TokenInfo> getOneByUuid (String uuid){
        return tokenInfoRepository.findById(uuid);
    }

    // 토큰 정보 조회 - accessToken
    public Optional<TokenInfo> getOneByAccessToken(String accessToken){
        return tokenInfoRepository.findByAccessToken(accessToken);
    }

    // 토큰 정보 조회 - refreshToken
    public Optional<TokenInfo> getOneByRefreshToken(String refreshToken){
        return tokenInfoRepository.findByRefreshToken(refreshToken);
    }

}
