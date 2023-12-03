package com.wesell.authenticationserver.service.userservice;

import com.wesell.authenticationserver.domain.repository.FindIDRepository;
import com.wesell.authenticationserver.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FindIDFeignService {

    private final FindIDRepository findIDRepository;

    public String findId(String uuid) throws UserNotFoundException {

        return findIDRepository.findEmailByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));

    }
}
