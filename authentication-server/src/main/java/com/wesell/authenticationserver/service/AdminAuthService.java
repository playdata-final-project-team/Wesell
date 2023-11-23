package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.enum_.Role;
import com.wesell.authenticationserver.domain.repository.AdminAuthUserRepository;
import com.wesell.authenticationserver.dto.request.AdminAuthIsForcedRequestDto;
import com.wesell.authenticationserver.dto.response.AdminAuthIsForcedResponseDto;
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

    public AdminAuthIsForcedResponseDto updateIsForced(AdminAuthIsForcedRequestDto requestDto) {
        AuthUser authUser = adminAuthUserRepository.findByUuid(requestDto.getUuid());

        if (authUser != null) {
            authUser.changeIsForced();
            return new AdminAuthIsForcedResponseDto(requestDto.getUuid() + " UUID를 가진 사용자가 강제 탈퇴로 표시되었습니다.");
        } else {
            return new AdminAuthIsForcedResponseDto(requestDto.getUuid() + " UUID를 가진 사용자를 찾을 수 없습니다.");
        }
    }
}
