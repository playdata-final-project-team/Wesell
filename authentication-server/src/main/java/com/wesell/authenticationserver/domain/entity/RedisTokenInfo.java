package com.wesell.authenticationserver.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("tokenInfo")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RedisTokenInfo {

    /**
     * @RedisHash : Redis의 Hash 자료구조(k-v map) 를 의미.
     * JPA @Entity에 해당하는 어노테이션으로 생각하면 된다. 어노테이션의 value는 key의 prefix가 된다.
     *
     * @Id : key를 식별하기 위한 고유 값.
     *
     * @Indexed : CRUD Repository를 사용 시 jpa의 findBy 필드명과 같이 사용하기 위해서 필요한 어노테이션.
     *
     * @TimeToLive : 유효시간(초 단위). 유효 시간이 지나면 자동으로 삭제.
     *  - @TimeToLive(unit = TimeUnit.MILLISECONDS) 옵션으로 단위 변경이 가능.
     */

    @Id
    String uuid;
    String refreshToken;
    String accessToken;

    public RedisTokenInfo updateAccessToken(String accessToken){
        this.accessToken = accessToken;
        return this;
    }

    public RedisTokenInfo updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
        return this;
    }

}
