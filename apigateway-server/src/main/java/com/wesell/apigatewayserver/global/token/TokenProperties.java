package com.wesell.apigatewayserver.global.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("token")
public class TokenProperties {

    private String issuer;
    private String secretKey;
}
