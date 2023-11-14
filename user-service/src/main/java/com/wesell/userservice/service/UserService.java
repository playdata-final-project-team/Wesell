package com.wesell.userservice.service;

import com.wesell.userservice.dto.RequestSignupDTO;
import com.wesell.userservice.dto.responseDto;
import com.wesell.userservice.exception.UserNotFoundException;
import com.wesell.userservice.domain.entity.User;
import com.wesell.userservice.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public responseDto findUser(String uuid) throws UserNotFoundException { // uuid로 유저 조회
        Optional<User> userOptional = userRepository.findByOneId(uuid);
        User user = userOptional.orElseThrow(() ->
                new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
        return responseDto.of(user);
    }

    public List<responseDto> findUsers() throws UserNotFoundException { // 유저 전체 조회
        Optional<List<User>> usersOptional = userRepository.findAll();
        List<User> userList = usersOptional.orElseThrow(() ->
                new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
        return responseDto.of(userList);
    }

    @Transactional
    public void deleteUser(String uuid) throws UserNotFoundException {   // 유저 한 명 삭제
        Optional<User> userOptional = userRepository.findByOneId(uuid);

        if(userOptional.isPresent())
            userRepository.delete(userOptional.get());
        else
            throw new UserNotFoundException("존재하지 않는 회원입니다.");

    public void save(RequestSignupDTO requestSignupDTO){
        User userEntity = UserService.convertToentity(requestSignupDTO);
        userRepository.save(userEntity);
    }

    public static User convertToentity(RequestSignupDTO userdto){
        return User.builder()
                .name(userdto.getName())
                .nickname(userdto.getNickname())
                .phone(userdto.getPhone())
                .agree(userdto.isAgree())
                .uuid(userdto.getUuid())
                .build();

    }

}
