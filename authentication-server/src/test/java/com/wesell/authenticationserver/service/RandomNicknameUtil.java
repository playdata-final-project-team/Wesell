package com.wesell.authenticationserver.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

public class RandomNicknameUtil {

    @Test
    @DisplayName("Test: 랜덤 영소문자로 닉네임 만들기")
    public void makeTest(){
        int length = 5;
        Random random = new Random();

        StringBuilder nickname = new StringBuilder(length);

        nickname.append("#");
        for(int i=0; i < length; i++){
            char randomChar = (char)('a' + random.nextInt(26));
            nickname.append(randomChar);
        }

        String date = LocalDateTime.now().toString();
        System.out.println("before ==> " + date);
        date = date.substring(date.lastIndexOf('.')+1);
        nickname.append(date);

        System.out.println(nickname.toString());
    }
}
