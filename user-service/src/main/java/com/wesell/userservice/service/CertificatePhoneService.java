package com.wesell.userservice.service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class CertificatePhoneService {

    @Value("${coolsms.api.key}")
    private String api_key;
    @Value("${coolsms.api.secret}")
    private String api_secret;

    public void certifiedPhoneNumber(String phoneNumber, String numStr) {

        Message coolsms = new Message(api_key, api_secret);


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);
        params.put("from", "01025246373");
        params.put("type", "SMS");
        params.put("text", " + 작성할내용 " + "["+numStr+"]" + "내용 ");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

    }
}
