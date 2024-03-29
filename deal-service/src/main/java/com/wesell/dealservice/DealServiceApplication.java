package com.wesell.dealservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DealServiceApplication {

    static {System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");}
    public static void main(String[] args) {
        SpringApplication.run(DealServiceApplication.class, args);
    }

}