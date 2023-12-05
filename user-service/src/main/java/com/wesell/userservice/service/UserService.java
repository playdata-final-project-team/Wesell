package com.wesell.userservice.service;

import com.wesell.userservice.dto.feigndto.EmailInfoDto;
import com.wesell.userservice.dto.request.SignupRequestDto;
import com.wesell.userservice.dto.response.MypageResponseDto;
import com.wesell.userservice.dto.response.ResponseDto;
import com.wesell.userservice.exception.UserNotFoundException;
import com.wesell.userservice.domain.entity.User;
import com.wesell.userservice.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public ResponseDto findUser(String uuid) throws UserNotFoundException { // uuid로 유저 조회
        Optional<User> userOptional = userRepository.findByOneId(uuid);
        User user = userOptional.orElseThrow(() ->
                new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
        return ResponseDto.of(user);
    }

    public List<ResponseDto> findUsers() throws UserNotFoundException { // 유저 전체 조회
        Optional<List<User>> usersOptional = userRepository.findAll();
        List<User> userList = usersOptional.orElseThrow(() ->
                new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
        return ResponseDto.of(userList);
    }

    @Transactional
    public void deleteUser(String uuid) throws UserNotFoundException {   // 유저 한 명 삭제
        Optional<User> userOptional = userRepository.findByOneId(uuid);

        if (userOptional.isPresent())
            userRepository.delete(userOptional.get());
        else
            throw new UserNotFoundException("존재하지 않는 회원입니다.");
    }

    @Transactional
    public void save(SignupRequestDto signupRequestDto) {
        User userEntity = UserService.convertToEntity(signupRequestDto);
        userRepository.save(userEntity);
    }

    public static User convertToEntity(SignupRequestDto userdto) {
        return User.builder()
                .name(userdto.getName())
                .nickname(userdto.getNickname())
                .phone(userdto.getPhone())
                .agree(userdto.isAgree())
                .uuid(userdto.getUuid())
                .isforced(userdto.isIsforced())
                .build();
    }

    public Optional<String> getNicknameByUuid(String uuid) {
        return userRepository.findNicknameByUuid(uuid);
    }

    @Transactional
    public void updateUser(String uuid, SignupRequestDto signupRequestDTO) {
        Optional<User> optionalUser = userRepository.findByOneId(uuid);

        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            User user = updatedUser.changeUserInfo(signupRequestDTO.getName());
            userRepository.update(user);
        } else {
            throw new EntityNotFoundException("User not found with uuid: " + uuid);
        }
    }
    @Transactional
    public void isforced(String uuid, SignupRequestDto signupRequestDTO) {
        Optional<User> optionalUser = userRepository.findByOneId(uuid);

        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            updatedUser.changeUserisforced(signupRequestDTO.isIsforced());
        } else {
            throw new EntityNotFoundException("User not found with uuid: " + uuid);
        }
    }

    public MypageResponseDto getMyPageDetails(String uuid) {
        Optional<User> userOptional = userRepository.findByOneId(uuid);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                EmailInfoDto emailInfoDto = emailService.getEmailInfo(uuid);

                return MypageResponseDto.builder()
                        .name(user.getName())
                        .nickname(user.getNickname())
                        .phone(user.getPhone())
                        .email(emailInfoDto.getEmail())
                        .build();
            } else {
                throw new NoSuchElementException("User not found");
        }
    }

    public String findIDPWD(String phone) throws UserNotFoundException {

        return userRepository.findUuidByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
    }
}

