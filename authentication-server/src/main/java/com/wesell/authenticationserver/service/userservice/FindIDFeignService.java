package com.wesell.authenticationserver.service.userservice;

import com.wesell.authenticationserver.domain.repository.FindIDRepository;
import com.wesell.authenticationserver.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindIDFeignService {

    private final FindIDRepository findIDRepository;

    public String findId(String uuid) throws UserNotFoundException {
        Optional<String> userOptional = findIDRepository.findEmailByUuid(uuid);

        if(userOptional.isPresent()) {

            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("유저 정보를 찾을 수 없습니다.");
        }
    }

}
