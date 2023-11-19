package com.wesell.userservice.controller;

import com.wesell.userservice.service.CertificatePhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class CertificatePhoneController {

    private final CertificatePhoneService certificatePhoneService;

    @RequestMapping("/sendSMS1.do")
    public ResponseEntity<String> sendSMS(String phoneNumber) {
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        certificatePhoneService.certifiedPhoneNumber(phoneNumber, numStr);

        return ResponseEntity.ok(numStr);
    }
}
