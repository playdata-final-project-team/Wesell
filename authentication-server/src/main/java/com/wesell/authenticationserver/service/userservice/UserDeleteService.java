package com.wesell.authenticationserver.service.userservice;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.repository.UserDeleteRepository;
import com.wesell.authenticationserver.global.util.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDeleteService {
    private final UserDeleteRepository userDeleteRepository;
    private final CustomPasswordEncoder passwordEncoder;

    @Transactional
    public void deleteByUser(String uuid, String pwd) {
        AuthUser authUser = userDeleteRepository.findByUuid(uuid);

        if(passwordEncoder.matches(pwd, authUser.getPassword())) {
            authUser.changeIsDeleted();
            userDeleteRepository.save(authUser);
        }

    }
}