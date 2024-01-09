package com.wesell.authenticationserver.service.userservice;

import com.wesell.authenticationserver.controller.response.CustomException;
import com.wesell.authenticationserver.controller.response.ErrorCode;
import com.wesell.authenticationserver.domain.repository.FindIDRepository;
import com.wesell.authenticationserver.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindIDFeignService {

    private final FindIDRepository findIDRepository;

    public String findId(String uuid) {
        return findIDRepository.findEmailByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

    }
}
