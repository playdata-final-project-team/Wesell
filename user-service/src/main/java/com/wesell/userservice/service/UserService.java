package com.wesell.userservice.service;

import com.wesell.userservice.dto.feigndto.EmailInfoDto;
import com.wesell.userservice.dto.request.SignupRequestDto;
import com.wesell.userservice.dto.response.MypageResponseDto;
import com.wesell.userservice.dto.response.ResponseDto;
import com.wesell.userservice.error.exception.UserNotFoundException;
import com.wesell.userservice.domain.entity.User;
import com.wesell.userservice.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.wesell.userservice.error.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public ResponseDto findUser(String uuid) { // uuid로 유저 조회
        User user = userRepository.findByOneId(uuid).orElseThrow(
                () -> new UserNotFoundException(INTER_SERVER_ERROR)
        );

        return ResponseDto.of(user);
    }

    public List<ResponseDto> findUsers() { // 유저 전체 조회
        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()) {
            throw new UserNotFoundException(INTER_SERVER_ERROR);
        }
        else {
            return ResponseDto.of(userList);
        }


    }

    @Transactional
    public void deleteUser(String uuid) throws UserNotFoundException {   // 유저 한 명 삭제

       User user = userRepository.findByOneId(uuid).orElseThrow(
                () -> new UserNotFoundException(INTER_SERVER_ERROR)
        );

        userRepository.delete(user);
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
                .build();
    }

    public String getNicknameByUuid(String uuid) {
        return userRepository.findNicknameByUuid(uuid).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND_NICKNAME)
        );
    }

    @Transactional
    public void updateUser(String uuid, SignupRequestDto signupRequestDTO) {
        User user = userRepository.findByOneId(uuid).orElseThrow(
                () -> new UserNotFoundException(INTER_SERVER_ERROR)
        );

        user.changeUserInfo(signupRequestDTO.getName());
        userRepository.save(user);
    }

    public MypageResponseDto getMyPageDetails(String uuid) {
        User user = userRepository.findByOneId(uuid).orElseThrow(
                () -> new UserNotFoundException(INTER_SERVER_ERROR)
        );

        EmailInfoDto emailInfoDto = emailService.getEmailInfo(uuid);

            return MypageResponseDto.builder()
                    .name(user.getName())
                    .nickname(user.getNickname())
                    .phone(user.getPhone())
                    .email(emailInfoDto.getEmail())
                    .build();
        }


    public String findIDPWD(String phone) throws UserNotFoundException {
        return userRepository.findUuidByPhone(phone).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND_PHONE)
        );
    }
}

