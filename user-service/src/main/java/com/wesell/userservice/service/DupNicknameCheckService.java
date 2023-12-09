package com.wesell.userservice.service;

import com.wesell.userservice.domain.repository.NicknameCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DupNicknameCheckService {
    private final NicknameCheckRepository repository;

    public void checkNickname(String nickname){
        if(repository.existsUserByNickname(nickname)) throw new RuntimeException("닉네임 중복!");
    }
}
