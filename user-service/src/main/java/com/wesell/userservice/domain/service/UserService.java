package com.wesell.userservice.domain.service;

import com.wesell.userservice.dto.request.SignupRequestDto;
import com.wesell.userservice.dto.response.MypageResponseDto;

public interface UserService {
    // 회원 가입
    public void save(SignupRequestDto requestDto);

    // 회원 정보 수정
    public void update(String uuid, SignupRequestDto requestDto);

    // 회원 삭제
    public void delete(String uuid);

    // 닉네임 중복 여부
    public void checkNickname(String nickname);

    // 회원 정보 조회
    public MypageResponseDto getMyInfo(String uuid);

    // 회원 전체 조회
//    public List<ResponseDto> findAll();

    // 휴대전화로 uuid 조회
    public String getUuidByPhone(String phoneNumber);

    // 회원 닉네임 조회
    public String getNicknameByUuid(String uuid);

    public Long updateDealCount(String uuid);
}
