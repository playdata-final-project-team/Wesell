package com.wesell.authenticationserver.domain.service;

import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.controller.dto.request.*;
import com.wesell.authenticationserver.controller.response.ResponseDto;
import com.wesell.authenticationserver.service.dto.oauth.KakaoInfo;
import com.wesell.authenticationserver.service.dto.response.SendSmsResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    // 회원 가입
    public ResponseEntity<ResponseDto> createUser(CreateUserRequestDto requestDto);

    // 로그인
    public GeneratedTokenDto login(SignInUserRequestDto requestDto);

    // 소셜 로그인
    public GeneratedTokenDto findOrCreateUser(KakaoInfo kakaoInfo);

    // 관리자 - 권한 변경
    public void updateRole(ChangeRoleRequestDto requestDto);

    // 관리자 - 회원 강제 탈퇴
    public void updateIsForced(String uuid);

    // 회원 탈퇴 전 비밀번호 확인
    public void checkPwForDelete(DeleteUserPwCheckRequestDto requestDto);

    // 회원 탈퇴
    public void deleteUser(String uuid);

    // 이메일 찾기
    public String findEmail(String uuid);

    // 전화번호로 회원 조회
    public SendSmsResponseDto findUserByPhone(String phone);

    // 이메일로 회원 조회
    public void findUserByEmail(String email);

    // 비밀번호 변경
    public void updatePw(UpdatePwRequestDto requestDto);

}
