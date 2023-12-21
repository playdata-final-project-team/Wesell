package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.enum_.Role;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
public class TokenProviderTest {

    private GeneratedTokenDto tokenDto;

    @BeforeEach
    public void setUpTest(){
        System.out.println("## BeforeEach! ##");

        AuthUser authUser = AuthUser.builder()
                .uuid("1")
                .email("a@test.com")
                .password("1")
                .role(Role.USER)
                .isDeleted(false)
                .isForced(false)
                .build();

        System.out.println("## AuthUser 생성하기 ##");

        Date now = new Date();
        String sectionId = UUID.randomUUID().toString();

        // 기한 만료된 token 생성 로직
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setHeaderParam("alg","HS256")
                .setHeaderParam("sectionId", sectionId)
                .setSubject(authUser.getUuid())
                .claim("role",authUser.getRole())
                .setIssuer("issuer")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofSeconds(1L).toMillis()))
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setHeaderParam("alg","HS256")
                .setHeaderParam("sectionId", sectionId)
                .setSubject(authUser.getUuid())
                .claim("role",authUser.getRole())
                .setIssuer("issuer")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofHours(1L).toMillis()))
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();

        tokenDto = new GeneratedTokenDto("1","USER",accessToken,refreshToken);
    }

    @Test
    @DisplayName("Test : 토큰 파싱 테스트")
    public void accessTokenValidate(){
        //given
        String accessToken = tokenDto.getAccessToken();
        String result = "";

        //when
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("secret")
                    .requireIssuer("issuer")
                    .parseClaimsJws(accessToken)
                    .getBody();
            System.out.println(claims.get("sectionId"));
            result += "+";
        }catch(ExpiredJwtException ex){
            result += "-";
        }catch(Exception e){
            result += "_";
        }

        //then
        Assertions.assertEquals("-",result);
        System.out.println("테스트 결과 : "+ result);
    }

    @Test
    @DisplayName("Test : token에서 sectionId 추출하기")
    public void getSectionId(){
        //given
        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();
        String result = "";

        //when
        String refreshHeaderParam = refreshToken.split("\\.")[0];
        if(refreshHeaderParam.equals(accessToken.split("\\.")[0])){
            result += "-";
        }

        //then
        Assertions.assertEquals("-",result);
    }

}
