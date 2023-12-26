package com.wesell.userservice.service;

import com.wesell.userservice.controller.response.CustomException;
import com.wesell.userservice.domain.repository.NicknameCheckRepository;
import com.wesell.userservice.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DupNicknameCheckService {
    private final NicknameCheckRepository repository;

    public void checkNickname(String nickname){
        if(repository.existsUserByNickname(nickname)){
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }
}
