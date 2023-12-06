package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.global.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("user-service")
@RequiredArgsConstructor
public class CertificatePhoneController {

    private final SmsUtil smsUtil;


    @PostMapping("/phone/id/validate")
    public ResponseEntity<?> sendSMSById(@RequestBody String phoneNumber) {

        String numStr = smsUtil.createCode();
        System.out.println(phoneNumber);

        smsUtil.sendOne(phoneNumber,numStr);

        return ResponseEntity.ok(phoneNumber);
    }

    @PostMapping("/phone/pwd/validate")
    public ResponseEntity<?> sendSMSByPWD(@RequestBody String phoneNumber) {

        String numStr = smsUtil.createCode();
        System.out.println(phoneNumber);
        smsUtil.sendOne(phoneNumber, numStr);

        return ResponseEntity.ok(new Message());
    }
}