package com.wesell.userservice.controller;

import com.wesell.userservice.dto.feigndto.AdminFeignResponseDto;
import com.wesell.userservice.dto.request.SignupRequestDto;
import com.wesell.userservice.dto.response.AdminUserResponseDto;
import com.wesell.userservice.dto.response.DealUserResponseDto;
import com.wesell.userservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2")
public class UserFeignController {

    private final UserServiceImpl userService;

    // 관리자 - 회원 관리 - 회원 검색 + 페이징
    @GetMapping("search-users")
    public Page<AdminUserResponseDto> searchUsers(@RequestParam("name") String name,
                                                  @RequestParam("nickname") String nickname,
                                                  @RequestParam("phone") String phone,
                                                  @RequestParam("uuid") String uuid,
                                                  @RequestParam("page") int page,
                                                  @RequestParam("size") int size){
        return userService.searchUsers(name, nickname, phone, uuid, page, size);
    }

    // 관리자 - 회원 관리 - 회원 전체 목록 + 페이징
    @GetMapping("user-list")
    public Page<AdminFeignResponseDto> getUserList(@RequestParam("page") int page,
                                                   @RequestParam("size") int size){
        return userService.getUserList(page, size);
    }

    // 인증/인가 - SMS 인증 번호 - 회원 uuid 조회
    @PostMapping("users/phone/uuid")
    public String findID(@RequestBody String phoneNumber) {
        return userService.getUuidByPhone(phoneNumber);
    }

    // 인증/인가 - 회원 가입
    @PostMapping("sign-up")
    public void signup(@RequestBody SignupRequestDto signupRequestDTO) {
        userService.save(signupRequestDTO);
    }

    // 인증/인가 - 회원 탈퇴
    @DeleteMapping("users/{uuid}")
    public void deleteUserEntity(@PathVariable String uuid) {
        userService.delete(uuid);
    }

    // 판매글 - 판매 상세 - 닉네임, 판매횟수 조회
    @GetMapping("users/{uuid}/dealInfo")
    public ResponseEntity<DealUserResponseDto> getDealInfoByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(userService.getDealInfo(uuid));
    }
    // 게시판 닉네임 조회
    @PostMapping("FindNickName")
    public String findNicknameByUuid(@RequestBody String uuid) {

        return userService.getNicknameByUuid(uuid);

    }
}
