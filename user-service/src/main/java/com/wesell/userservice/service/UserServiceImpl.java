package com.wesell.userservice.service;

import com.wesell.userservice.controller.response.CustomException;
import com.wesell.userservice.domain.repository.UserRepository;
import com.wesell.userservice.domain.repository.UserSelectRepository;
import com.wesell.userservice.domain.service.UserService;
import com.wesell.userservice.dto.feigndto.AdminFeignResponseDto;
import com.wesell.userservice.dto.feigndto.AuthUserInfoRequestDto;
import com.wesell.userservice.dto.request.SignupRequestDto;
import com.wesell.userservice.dto.response.AdminUserResponseDto;
import com.wesell.userservice.dto.response.MypageResponseDto;
import com.wesell.userservice.dto.response.ResponseDto;
import com.wesell.userservice.error.exception.ErrorCode;
import com.wesell.userservice.error.exception.UserNotFoundException;
import com.wesell.userservice.domain.entity.User;
import com.wesell.userservice.service.feign.AuthServerFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wesell.userservice.error.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserSelectRepository userSelectRepository;
    private final AuthServerFeignClient authServerFeignClient;

    /**
     * 마이페이지 - 회원 개별 조회
     *
     * @param uuid
     * @return
     */
    @Override
    public MypageResponseDto getMyInfo(String uuid) {
        User user = userRepository.findById(uuid).orElseThrow(
                /**
                 * 공통 응답 로직 구현 후 수정
                 */
        );

        String email = authServerFeignClient.getEmail(uuid);

        return MypageResponseDto.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .email(email)
                .build();
    }

    /**
     * 마이페이지 - 회원 정보 수정
     *
     * @param requestDto
     */
    @Override
    public void update(String uuid, SignupRequestDto requestDto) {
        User user = userRepository.findById(uuid).orElseThrow(
                /**
                 * 공통 응답 로직 구현 후 수정
                 */
        );
        user.changeUserInfo(requestDto.getName());
        userRepository.saveAndFlush(user);
    }

    /**
     * 회원 전체 목록 조회
     *
     * @return
     */
    @Override
    public List<ResponseDto> findAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            /**
             * 공통 응답 로직 구현 후 수정
             */
            return null;
        } else {
            return ResponseDto.of(users);
        }
    }

    /**
     * 회원 탈퇴
     *
     * @param uuid
     * @throws UserNotFoundException
     */
    @Override
    public void delete(String uuid) {
        User user = userRepository.findById(uuid).orElseThrow(
                /**
                 * 공통 응답 로직 구현 후 수정
                 */
        );
        userRepository.delete(user);
    }

    /**
     * 회원 가입
     *
     * @param signupRequestDto
     */
    @Transactional
    @Override
    public void save(SignupRequestDto signupRequestDto) {
        User userEntity = convertToEntity(signupRequestDto);
        userRepository.save(userEntity);
    }

    /**
     * 회원가입 - 닉네임 중복 확인
     *
     * @param nickname
     */
    @Override
    public void checkNickname(String nickname) {
        if (userRepository.existsUserByNickname(nickname)) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }

    /**
     * 휴대전화로 회원 uuid 조회
     *
     * @param phone
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public String getUuidByPhone(String phone) throws UserNotFoundException {
        return userSelectRepository.findUuidByPhone(phone).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND_PHONE)
        );
    }

    /**
     * 회원 닉네임 조회
     *
     * @param uuid
     * @return
     */
    @Override
    public String getNicknameByUuid(String uuid) {
        return userSelectRepository.findNicknameByUuid(uuid).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND_NICKNAME)
        );
    }

    /**
     * 관리자 - 회원관리 - 회원 검색 + 회원 목록 조회 - 페이징 처리
     *
     * @param name
     * @param nickname
     * @param phone
     * @param uuid
     * @param page
     * @param size
     * @return
     */
    public Page<AdminUserResponseDto> searchUsers(String name,
                                                  String nickname,
                                                  String phone,
                                                  String uuid,
                                                  int page,
                                                  int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 페이징된 검색 결과를 가져옴
        Page<User> pagedUsers = userRepository.findByNameContainingOrNicknameContainingOrPhoneContainingOrUuidContaining(
                name, nickname, phone, uuid, pageable
        );

        // 페이징된 결과를 AdminUserResponseDto로 변환하여 반환
        return pagedUsers.map(user -> AdminUserResponseDto.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .uuid(user.getUuid())
                .build()
        );
    }

    /**
     * 관리자 - 회원관리 - 회원 전체 목록 조회 - 페이징 처리
     *
     * @param page
     * @param size
     * @return
     */
    public Page<AdminFeignResponseDto> getUserList(int page, int size) {
        // 사용자 정보를 페이징해서 가져옴
        Page<User> userInfoPage = userRepository.findAll(PageRequest.of(page, size));

        List<AuthUserInfoRequestDto> authUserInfoList = authServerFeignClient.getListAuthUserInfo();

        // 페이징된 응답 DTO 리스트 생성
        List<AdminFeignResponseDto> responseDtoList = userInfoPage.getContent().stream().map(user ->
                AdminFeignResponseDto.builder()
                        .uuid(user.getUuid())
                        .name(user.getName())
                        .phone(user.getPhone())
                        .nickname(user.getNickname())
                        .build()
        ).toList();

        // AuthUserInfo 리스트에서 매칭되는 정보 찾아 업데이트
        for (AdminFeignResponseDto dto : responseDtoList) {
            String uuid = dto.getUuid();

            for (AuthUserInfoRequestDto authDto : authUserInfoList) {
                if (authDto.getUuid().equals(uuid)) {
                    dto.setEmail(authDto.getEmail());
                    dto.setRole(authDto.getRole());
                }
            }
        }

        // 페이징된 데이터를 포함하는 Page 객체로 변환하여 반환
        return new PageImpl<>(responseDtoList, userInfoPage.getPageable(), userInfoPage.getTotalElements());
    }

    // 엔티티로 변환
    private User convertToEntity(SignupRequestDto requestDto) {
        return User.builder()
                .name(requestDto.getName())
                .nickname(requestDto.getNickname())
                .phone(requestDto.getPhone())
                .agree(requestDto.isAgree())
                .uuid(requestDto.getUuid())
                .build();
    }
}
