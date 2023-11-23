package com.wesell.userservice.controller;

import com.wesell.userservice.global.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user-service")
@RequiredArgsConstructor
public class CertificatePhoneController {


    private final SmsUtil smsUtil;

    @PostMapping("/phone/validate")
    public ResponseEntity<?> sendSMS(@RequestBody String phoneNumber) {

        String numStr = smsUtil.createCode();
        System.out.println(phoneNumber);
        smsUtil.sendOne(phoneNumber,numStr);

        return ResponseEntity.ok(new Message());
    }
}