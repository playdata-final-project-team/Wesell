package com.wesell.authenticationserver.global.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

public class RandomNicknameUtil {

    public static String makeRandomNickname(){
        int length = 5;
        Random random = new Random();

        StringBuilder nickname = new StringBuilder(length);

        nickname.append("#");
        for(int i=0; i < length; i++){
            char randomChar = (char)('a' + random.nextInt(26));
            nickname.append(randomChar);
        }

        String date = LocalDateTime.now().toString();
        date = date.substring(date.lastIndexOf('.')+1);
        nickname.append(date);

        return nickname.toString();
    }
}
