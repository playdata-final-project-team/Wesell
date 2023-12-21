package com.wesell.authenticationserver.global.util;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class SmsUtil {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    private DefaultMessageService messageService;
    private String storedCode; // 인증번호를 저장할 변수

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송 예제
    public void sendOne(String to, String verificationCode) {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01051150351");
        message.setTo(to.trim());
        message.setText("아래의 인증번호를 입력해주세요 \n" + verificationCode);

        this.messageService.sendOne(new SingleMessageSendingRequest(message));

        this.storedCode = verificationCode;
    }

    // 인증번호 일치 여부를 판단하는 메서드
    public boolean isCodeValid(String code) {
        if (storedCode != null && storedCode.equals(code)) {
            // 인증번호 일치
            return true;
        } else {
            // 인증번호 불일치
            return false;
        }
    }

    // 인증번호 생성
    public String createCode(){
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        return numStr;
    }

}