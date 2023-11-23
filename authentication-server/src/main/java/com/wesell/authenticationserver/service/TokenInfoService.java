package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.domain.entity.TokenInfo;
import com.wesell.authenticationserver.domain.repository.TokenInfoRepository;
import com.wesell.authenticationserver.dto.GeneratedTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * 토큰 정보 관련 기능
 */
@Service
@RequiredArgsConstructor
public class TokenInfoService {

    private final TokenInfoRepository tokenInfoRepository;

    /**
     * 토큰 정보 저장 및 갱신
     * dto 별도의 값들을 따로 받는 이유,
     * GeneratedTokenDto 으로 종속되어 기능 확장에 제한이 걸림
     */
    @Transactional
    public TokenInfo saveOrUpdate(GeneratedTokenDto dto, String uuid, String newRefreshToken, String newAccessToken){
        Optional<TokenInfo> optTokenInfo = tokenInfoRepository.findById(uuid);

        if(optTokenInfo.isPresent() && newRefreshToken != null){ // refresh-token 수정

            TokenInfo tokenInfo = optTokenInfo.get();
            tokenInfo.updateRefreshToken(newRefreshToken);
            return tokenInfoRepository.save(tokenInfo);

        }else if(optTokenInfo.isPresent() && newAccessToken != null){ // access-token 수정

            TokenInfo tokenInfo = optTokenInfo.get();
            tokenInfo.updateAccessToken(newAccessToken);
            return tokenInfoRepository.save(tokenInfo);

        }else{ // 새롭게 저장

            TokenInfo tokenInfo = new TokenInfo(
                    dto.getUuid(),
                    dto.getRefreshToken(),
                    dto.getAccessToken()
            );
            return tokenInfoRepository.save(tokenInfo);

        }
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
