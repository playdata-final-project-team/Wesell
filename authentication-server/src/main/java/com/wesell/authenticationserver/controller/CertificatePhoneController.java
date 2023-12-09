package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.global.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CertificatePhoneController {

    private final SmsUtil smsUtil;


    @GetMapping("/phone/validate")
    public ResponseEntity<?> sendSMSById(@RequestParam String phoneNumber) {

        String numStr = smsUtil.createCode();
        System.out.println(phoneNumber);

        smsUtil.sendOne(phoneNumber,numStr);

        return ResponseEntity.ok(phoneNumber);
    }

}