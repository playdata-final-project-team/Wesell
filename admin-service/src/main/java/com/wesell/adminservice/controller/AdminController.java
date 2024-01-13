package com.wesell.adminservice.controller;

import com.wesell.adminservice.domain.service.AdminService;
import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;;
import com.wesell.adminservice.global.response.success.SuccessApiResponse;
import com.wesell.adminservice.global.response.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AdminController {

    private final AdminService adminService;

//    @GetMapping("get-version") //버전 값 불러오기//기본값도 들어가있다
//    public ResponseEntity<Map<String, String>> getVersions() {
//        return new ResponseEntity<>(versionServiceImpl.getVersions(), HttpStatus.OK);
//    }

//    @PostMapping("version") //버전저장
//    public ResponseEntity<Map<String, String>> getVersionAndSave(
//            @RequestParam(name = "jsVersion", defaultValue = "1.0") String jsVersion,
//            @RequestParam(name = "cssVersion", defaultValue = "1.0") String cssVersion,
//            @RequestParam(name = "title", defaultValue = "Default Title") String title,
//            @RequestParam(name = "agree", defaultValue = "개인정보 제공에 동의하십니까?") String agree) {
//        return new ResponseEntity<>(versionServiceImpl.setVersions(jsVersion, cssVersion, title, agree), HttpStatus.OK);
//    }

    @GetMapping("get/users")//유저리스트
    public ResponseEntity<?> getUserList(@RequestParam("page") int page,
                                                 @RequestParam("size") int size) {
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        SuccessCode.OK,adminService.getUserList(page, size)
                )
        );
    }

    @PutMapping("change-role") //유저권한수정
    public ResponseEntity<?> changeUserRole(@RequestBody ChangeRoleRequestDto requestDto) {
        adminService.changeUserRole(requestDto);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }

    @PutMapping("updateIsForced/{uuid}") //회원강제탈퇴
    public ResponseEntity<?> updateIsForced(@PathVariable String uuid) {
        adminService.forcedDelete(uuid);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }

    @GetMapping("get/post") //판매글리스트
    public ResponseEntity<?> getPostList(@RequestParam("uuid") String uuid,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("size") int size){
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK,adminService.getPostList(uuid, page, size)));
    }
    
    @PutMapping("deletePost") //판매글삭제
    public ResponseEntity<?> deletePost(@RequestParam("uuid") String uuid,
                                        @RequestParam("id") Long postId) {
        adminService.deletePost(uuid, postId);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }

    @GetMapping("search") //유저검색
    public ResponseEntity<?> searchUsers(@RequestParam(name = "name", defaultValue = "") String name,
                                                                                      @RequestParam(name = "nickname", defaultValue = "") String nickname,
                                                                                      @RequestParam(name = "phone", defaultValue = "") String phone,
                                                                                      @RequestParam(name = "uuid", defaultValue = "") String uuid,
                                                                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                                                                      @RequestParam(name = "size", defaultValue = "10") int size)
    {
        return ResponseEntity.ok(SuccessApiResponse.of(
                SuccessCode.OK,
                adminService.searchUsers(name, nickname, phone, uuid, page, size)
        ));
    }
}
