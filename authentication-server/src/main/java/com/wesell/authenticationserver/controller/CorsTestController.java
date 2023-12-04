package com.wesell.authenticationserver.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsTestController {

    // CORS 테스트
    @GetMapping("api/v1/cors-test")
    public String index(){
        return "index";
    }

    @GetMapping("not-cors")
    public String notCors(){
        return "notCors";
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("cors")
    public String cors(){
        return "cors";
    }

    @GetMapping("not-proxy")
    public String notProxy(){
        return "notProxy";
    }

    @GetMapping("proxy")
    public String proxy(){
        return "proxy";
    }
}
