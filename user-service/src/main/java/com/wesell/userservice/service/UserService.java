package com.wesell.userservice.service;

import com.wesell.userservice.dto.feigndto.EmailInfoDto;
import com.wesell.userservice.dto.request.SignupRequestDto;
import com.wesell.userservice.dto.response.MypageResponseDto;
import com.wesell.userservice.dto.response.ResponseDto;
import com.wesell.userservice.error.exception.UserNotFoundException;
import com.wesell.userservice.domain.entity.User;
import com.wesell.userservice.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.wesell.userservice.error.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public ResponseDto findUser(String uuid) { // uuid로 유저 조회
        try {
            User user = userRepository.findByOneId(uuid).get();
            return ResponseDto.of(user);
        }catch (Exception e) {
            throw new UserNotFoundException(INTER_SERVER_ERROR);
        }
    }

    public List<ResponseDto> findUsers() throws UserNotFoundException { // 유저 전체 조회
        try {
            List<User> userList = userRepository.findAll().get();
            return ResponseDto.of(userList);
        }
        catch(Exception e) {
            throw new UserNotFoundException(INTER_SERVER_ERROR);
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
        try {
            String nickname = userRepository.findNicknameByUuid(uuid).get();
            return nickname;
        }catch (Exception e) {
            throw new UserNotFoundException(NOT_FOUND_NICKNAME);
        }

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
        try {
            String userId = userRepository.findUuidByPhone(phone).get();
            return userId;
        } catch (Exception e) {
            throw new UserNotFoundException(NOT_FOUND_PHONE);
        }

    }
}

