package com.wesell.authenticationserver.domain.service;

import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.controller.dto.request.*;
import com.wesell.authenticationserver.service.dto.oauth.KakaoInfo;

public interface AuthService {

    // 회원 가입
    void createUser(CreateUserRequestDto requestDto);

    // 로그인
    GeneratedTokenDto signIn(SignInUserRequestDto requestDto);

    // 소셜 로그인
    GeneratedTokenDto findOrCreateUser(KakaoInfo kakaoInfo);

    // 관리자 - 권한 변경
    void updateRole(ChangeRoleRequestDto requestDto);

    // 관리자 - 회원 강제 탈퇴
    void updateIsForced(String uuid);

    // 회원 탈퇴 전 비밀번호 확인
    void checkPwForDelete(DeleteUserPwCheckRequestDto requestDto);

    // 회원 탈퇴
    void deleteUser(String uuid);

    // 이메일 찾기
    String findEmail(String uuid);

    // 전화번호로 회원 조회
    String findUserByPhone(String phone);

    // 이메일로 회원 조회
    void findUserByEmail(String email);

    // 비밀번호 변경
    void updatePw(UpdatePwRequestDto requestDto);

    // 이메일 중복 조회
    void checkEmail(String email);

}
