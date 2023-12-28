package com.wesell.authenticationserver.service.userservice;

import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.repository.FindPWDRepository;
import com.wesell.authenticationserver.exception.UserNotFoundException;
import com.wesell.authenticationserver.global.util.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindPWDFeignService {
    private final FindPWDRepository findPWDRepository;
    private final CustomPasswordEncoder encoder;

    public String findUuid(String email) {
        String uuid = findPWDRepository.findUuidByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Optional<AuthUser> optionalAuthUser = findPWDRepository.findByUuid(uuid);

        if (optionalAuthUser.isPresent()) {
            if (optionalAuthUser.get().getPassword() == null) {
                throw new CustomException(ErrorCode.SOCIAL_LOGIN_FOUND_PWD);
            } else {
                return uuid;
            }
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
            }

        }


    public void setPassword(String uuid, String pwd, String rePwd) {
        Optional<AuthUser> authUserOptional = findPWDRepository.findByUuid(uuid);
        if(authUserOptional.isPresent()) {
            AuthUser authUser = authUserOptional.get();
            if(pwd.equals(rePwd)) {
                String encodedPassword = encoder.encode(pwd);
                authUser.changePassword(encodedPassword);
                findPWDRepository.saveAndFlush(authUser);
            }
        }else{
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
    }
}
