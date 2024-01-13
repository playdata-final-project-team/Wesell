package com.wesell.authenticationserver.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    // 버전 정보
    private final String VERSION = "v0.0.1";

    // 제목
    private final String TITLE = "\" AUTH SERVER API 명세서 \"";

    //설명
    private final String DESCRIPTION = "AUTH SERVER 관련 API 명세서 입니다."
            +"인증 및 인가 관련된 요청을 처리합니다.";

    /**
     * Swagger API 설정
     * @return
     */
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(swaggerInfo());
    }

    /**
     * Swagger UI 기본 정보 설정
     * @return Info()
     */
    private Info swaggerInfo(){
        return new Info()
                .version(VERSION)
                .title(TITLE)
                .description(DESCRIPTION);
    }
}
