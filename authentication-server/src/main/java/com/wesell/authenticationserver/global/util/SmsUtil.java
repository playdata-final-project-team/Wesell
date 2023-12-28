package com.wesell.authenticationserver.global.util;

import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
@Log4j2
public class SmsUtil {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${coolsms.api.from}")
    private String from;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송 예제
    public void sendOne(String to, String verificationCode) {
        try {
            Message message = new Message();
            message.setFrom("01047661265");
            message.setTo(to.trim());
            message.setText("아래의 인증번호를 입력해주세요 \n" + verificationCode);
            this.messageService.sendOne(new SingleMessageSendingRequest(message));
        }catch (Exception e){
            log.error("Error: {}",e);
            throw new CustomException(ErrorCode.SMS_VALID_FAIL);
        }
    }

    // 인증번호 일치 여부를 판단하는 메서드
//    public boolean isCodeValid(String code) {
//        if (storedCode != null && storedCode.equals(code)) {
//            // 인증번호 일치
//            return true;
//        } else {
//            // 인증번호 불일치
//            return false;
//        }
//    }

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