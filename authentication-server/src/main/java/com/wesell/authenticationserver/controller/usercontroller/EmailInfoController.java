package com.wesell.authenticationserver.controller.usercontroller;

import com.wesell.authenticationserver.dto.response.userdto.EmailInfoDto;
import com.wesell.authenticationserver.service.userservice.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EmailInfoController {

    private final EmailService emailService;

    @GetMapping("authentication-server/emailinfo/{uuid}")
    public ResponseEntity<EmailInfoDto> getUserByEmail(@PathVariable String uuid) {
        Optional<EmailInfoDto> emailInfoDto = emailService.getUserByEmail(uuid);
        EmailInfoDto dto = emailInfoDto.orElseThrow(
                () -> new RuntimeException("회원 정보가 없다!")
        );
        return ResponseEntity.ok(dto);
    }
}
