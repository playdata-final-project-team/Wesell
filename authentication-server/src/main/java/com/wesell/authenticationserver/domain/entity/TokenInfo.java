package com.wesell.authenticationserver.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
public class TokenInfo {

    @Id
    private String uuid;

    private String refreshToken;

    private String accessToken;

}
