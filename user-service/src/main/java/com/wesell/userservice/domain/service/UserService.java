package com.wesell.userservice.domain.service;

import com.wesell.userservice.domain.dto.UserDTO;
import com.wesell.userservice.domain.entity.User;
import com.wesell.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserDTO userDTO){
        User userEntity = User.toUserEntity(userDTO);
        userRepository.save(userEntity);
    }
}
