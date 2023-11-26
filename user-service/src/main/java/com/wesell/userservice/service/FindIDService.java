package com.wesell.userservice.service;

import com.wesell.userservice.domain.entity.User;
import com.wesell.userservice.domain.repository.UserRepository;
import com.wesell.userservice.dto.response.FindIDResponseDto;
import com.wesell.userservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FindIDService {

   private final UserRepository userRepository;

    public FindIDResponseDto findID(String phone) throws UserNotFoundException {
        String uuid = userRepository.findUuidByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
        User user = userRepository.findByOneId(uuid)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
        return FindIDResponseDto.of(user);
    }
}
