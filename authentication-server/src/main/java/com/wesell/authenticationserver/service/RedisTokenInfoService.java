package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.domain.entity.RedisTokenInfo;
import com.wesell.authenticationserver.domain.entity.TokenInfo;
import com.wesell.authenticationserver.domain.repository.RedisTokenInfoRepository;
import com.wesell.authenticationserver.dto.GeneratedTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisTokenInfoService {
    private final RedisTokenInfoRepository redisTokenInfoRepository;

    // Redis 로 토큰 저장 기능
    @Transactional
    public RedisTokenInfo saveOrUpdate(GeneratedTokenDto dto, String uuid, String newRefreshToken, String newAccessToken){
        Optional<RedisTokenInfo> optTokenInfo = redisTokenInfoRepository.findById(uuid);

        if(optTokenInfo.isPresent() && newRefreshToken != null){ // refresh-token 수정

            RedisTokenInfo redisTokenInfo = optTokenInfo.get();
            redisTokenInfo.updateRefreshToken(newRefreshToken);
            return redisTokenInfoRepository.save(redisTokenInfo);

        }else if(optTokenInfo.isPresent() && newAccessToken != null){ // access-token 수정

            RedisTokenInfo redisTokenInfo = optTokenInfo.get();
            redisTokenInfo.updateAccessToken(newAccessToken);
            return redisTokenInfoRepository.save(redisTokenInfo);

        }else{ // 새롭게 저장

            RedisTokenInfo redisTokenInfo = new RedisTokenInfo(
                    dto.getUuid(),
                    dto.getRefreshToken(),
                    dto.getAccessToken()
            );
            return redisTokenInfoRepository.save(redisTokenInfo);

        }
    }

//    public String getToken
}
