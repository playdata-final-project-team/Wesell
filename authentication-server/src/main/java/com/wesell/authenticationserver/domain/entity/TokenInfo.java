package com.wesell.authenticationserver.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenInfo {

    @Id
    private String uuid;

    private String refreshToken;

    private String accessToken;

    public TokenInfo updateAccessToken(String accessToken){
        this.accessToken = accessToken;
        return this;
    }

    public TokenInfo updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
        return this;
    }

}
