package com.wesell.userservice.service;

import com.wesell.userservice.dto.feigndto.EmailInfoDto;
import com.wesell.userservice.service.feign.AuthServerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final AuthServerFeignClient authServerFeignClient;

    public EmailInfoDto getEmailInfo(String uuid) {
        ResponseEntity<EmailInfoDto> response = authServerFeignClient.getEmailInfo(uuid);

        System.out.println("응답코드: " + response.getStatusCodeValue());

        if (response.getStatusCode().is2xxSuccessful()) {
            EmailInfoDto emailInfoDto = response.getBody();
            System.out.println("값: " + emailInfoDto.getEmail());
            return emailInfoDto;
        } else {
            System.out.println("에러응답: " + response.getBody());
            return null;
        }
    }
}