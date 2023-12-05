package com.wesell.authenticationserver.service.userservice;

import com.wesell.authenticationserver.domain.repository.EmailInfoRepository.EmailInfoRepository;
import com.wesell.authenticationserver.dto.response.userdto.EmailInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailInfoRepository emailInfoRepository;
    public Optional<EmailInfoDto> getUserByEmail(String uuid) {
        return emailInfoRepository.findByUuid(uuid).map(
                user -> new EmailInfoDto(user.getEmail()));
    }
}
