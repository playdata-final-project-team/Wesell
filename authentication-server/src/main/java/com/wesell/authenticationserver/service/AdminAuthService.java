package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.enum_.Role;
import com.wesell.authenticationserver.domain.repository.AdminAuthUserRepository;
import com.wesell.authenticationserver.domain.repository.AuthUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminAuthUserRepository adminAuthUserRepository;

    @Transactional
    public void updateRole(String uuid, Role newRole) {
        AuthUser user = adminAuthUserRepository.findByUuid(uuid);

        if (user != null) {
            user.changeRole(newRole);
            adminAuthUserRepository.save(user);
        }
    }
}
