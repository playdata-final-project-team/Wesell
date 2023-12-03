package com.wesell.authenticationserver.service.userservice;

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

    public String findUuid(String email) throws UserNotFoundException {
        Optional<String> optionalUser = findPWDRepository.findUuidByEmail(email);

        if(optionalUser.isPresent()) {

            return optionalUser.get();
        }
        else {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
    }

    public void setPassword(String uuid, String pwd, String rePwd) {
        Optional<AuthUser> authUserOptional = findPWDRepository.findByUuid(uuid);
        if(authUserOptional.isPresent()) {
            AuthUser authUser = authUserOptional.get();
            if(pwd.equals(rePwd)) {
                String encodedPassword = encoder.encode(pwd);
                authUser.changePassword(encodedPassword);
            }
        }
    }
}
