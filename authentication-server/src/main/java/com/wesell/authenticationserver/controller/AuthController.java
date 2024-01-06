package com.wesell.authenticationserver.controller;

import com.wesell.authenticationserver.controller.dto.GeneratedTokenDto;
import com.wesell.authenticationserver.controller.dto.request.*;
import com.wesell.authenticationserver.controller.response.ResponseDto;
import com.wesell.authenticationserver.domain.service.AuthService;
import com.wesell.authenticationserver.domain.service.TokenService;
import com.wesell.authenticationserver.global.util.CustomCookie;
import com.wesell.authenticationserver.service.AuthServiceImpl;
import com.wesell.authenticationserver.service.TokenServiceImpl;
import com.wesell.authenticationserver.service.dto.oauth.KakaoInfo;
import com.wesell.authenticationserver.service.dto.response.AdminAuthResponseDto;
import com.wesell.authenticationserver.service.dto.response.SendSmsResponseDto;
import com.wesell.authenticationserver.service.dto.response.SignInSuccessResponseDto;
import com.wesell.authenticationserver.controller.response.SuccessCode;
import com.wesell.authenticationserver.service.KakaoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("api/v1")
public class AuthController {

    private final AuthServiceImpl authService;
    private final TokenServiceImpl tokenService;
    private final KakaoService kakaoService;
    private final CustomCookie cookieUtil;

    // 회원가입
    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserRequestDto requestDto){
        log.info("client 회원가입 요청");
        return authService.createUser(requestDto);
    }

    // 로그인
    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInUserRequestDto requestDto){
        log.info("client 로그인 요청");
        GeneratedTokenDto generatedTokenDto = authService.login(requestDto);

        log.debug("AuthController - 액세스 토큰 쿠키 생성");
        ResponseCookie accessTokenCookie = cookieUtil.createAccessTokenCookie(generatedTokenDto.getAccessToken());
        ResponseCookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(generatedTokenDto.getRefreshToken());

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(new SignInSuccessResponseDto(generatedTokenDto.getUuid(), generatedTokenDto.getRole(),null));
    }

    // 소셜 로그인 - KAKAO
    @PostMapping("kakao/auth-code")
    public ResponseEntity<?> kakaoAuthCode(@RequestBody String authCode, HttpSession session){
        log.info("client 카카오 로그인 요청");
        KakaoInfo kakaoInfo = kakaoService.getInfo(authCode);

        log.debug("소셜 로그인 - 액세스 토큰 정보 session 에 저장");
        session.setAttribute(kakaoInfo.getId().toString(),kakaoInfo.getKakaoToken());

        log.debug("소셜 로그인 - 회원 확인 및 회원 가입");
        GeneratedTokenDto generatedTokenDto = authService.findOrCreateUser(kakaoInfo);

        log.debug("AuthController - 쿠키 생성");
        ResponseCookie accessTokenCookie = cookieUtil.createAccessTokenCookie(generatedTokenDto.getAccessToken());
        ResponseCookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(generatedTokenDto.getRefreshToken());

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(new SignInSuccessResponseDto(generatedTokenDto.getUuid(), generatedTokenDto.getRole(),kakaoInfo.getId()));
    }

    // 소셜 로그아웃 - KAKAO
    @GetMapping("kakao/logout/{kakaoId}")
    public ResponseEntity<?> kakaoLogout(@PathVariable("kakaoId") String kakaoId, HttpSession session){
        log.info("client 카카오 로그아웃 요청");
        Object token = session.getAttribute(kakaoId);

        if(!Objects.isNull(token)) {
            kakaoService.logout(token.toString());
        }
        session.removeAttribute(kakaoId);

        log.debug("쿠키 삭제");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();
        ResponseCookie deleteJSESSIONID = cookieUtil.deleteJSESSIONIDCookie();

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteJSESSIONID.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    // 만료된 토큰 갱신
    @GetMapping("refresh")
    public ResponseEntity<?> refresh(
            @CookieValue(name = "access-token", required = false) String accessToken,
            @CookieValue(name = "refresh-token") String refreshToken
            ){

        log.debug("AuthController - 토큰 갱신");
        GeneratedTokenDto generatedTokenDto = tokenService.refreshToken(refreshToken,accessToken);

        ResponseCookie newAccessTokenCookie = cookieUtil.createAccessTokenCookie(generatedTokenDto.getAccessToken());
        ResponseCookie newRefreshTokenCookie = cookieUtil.createRefreshTokenCookie(generatedTokenDto.getRefreshToken());

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,newAccessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE,newRefreshTokenCookie.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    // 로그아웃
    @GetMapping("logout")
    public ResponseEntity<?> logout(){
        log.debug("AuthController - 로그아웃");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    // 회원탈퇴 전 비밀번호 확인
    @PostMapping("delete/pw-check")
    public ResponseEntity<ResponseDto> checkPw(@RequestBody DeleteUserPwCheckRequestDto requestDto){
        log.debug("AuthController - 회원 탈퇴 전 비밀번호 확인");
        authService.checkPwForDelete(requestDto);
        return ResponseEntity.ok(ResponseDto.of(SuccessCode.OK));
    }

    // 회원 탈퇴
    @DeleteMapping("delete/{uuid}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable(value="uuid") String uuid){
        log.debug("AuthController - 회원 탈퇴");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();

        authService.deleteUser(uuid);

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE,deleteRefreshToken.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    // 카카오 회원 탈퇴
    @DeleteMapping("delete/kakao")
    public ResponseEntity<ResponseDto> deleteKakaoUser(@RequestParam String kakaoId,
                                                       @RequestParam String uuid,
                                                       HttpSession session){
        log.debug("AuthController - 카카오 회원 탈퇴");
        ResponseCookie deleteAccessToken = cookieUtil.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = cookieUtil.deleteRefreshTokenCookie();
        kakaoService.unlink(session.getAttribute(kakaoId).toString());

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .header(HttpHeaders.SET_COOKIE,deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE,deleteRefreshToken.toString())
                .body(ResponseDto.of(SuccessCode.OK));
    }

    /**
     * 번호 인증
     * return SendSmsResponseDto
     *  - uuid
     *  - certNum
     */
    @GetMapping("phone/validate")
    public ResponseEntity<?> sendSMS(@RequestParam String phoneNumber) {

        log.info("client 인증 번호 요청");

        log.debug("휴대전화 번호로 회원 조회");
        SendSmsResponseDto respDto =  authService.findUserByPhone(phoneNumber);
        return ResponseEntity.ok(respDto);
    }

    // 이메일 찾기 - 이메일 반환
    @GetMapping("find-email/{uuid}")
    public ResponseEntity<?> findEmail(@PathVariable("uuid") String uuid) {
        String email = authService.findEmail(uuid);
        /**
         * 공통 응답 로직 수정 시 추가 수정
         * ResponseDto에 담아서 반환
         */
        return ResponseEntity.ok(email);
    }

    // 비밀번호 찾기 - 이메일 존재 여부 확인
    @GetMapping("email-check")
    public ResponseEntity<?> checkEmail(@RequestParam String email){
        authService.findUserByEmail(email);
        /**
         * 공통 응답 로직 수정 시 추가 수정
         * ResponseDto에 담아서 반환
         */
        return ResponseEntity.ok(ResponseDto.of(SuccessCode.OK));
    }

    // 비밀번호 찾기 - 비밀번호 수정
    @PostMapping("update-pw")
    public ResponseEntity<?> updatePWD(@RequestBody UpdatePwRequestDto requestDto) {
        authService.updatePw(requestDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // 권한 변경
    @PutMapping("change-role")
    public ResponseEntity<?> changeUserRole(@RequestBody ChangeRoleRequestDto requestDto) {
        authService.updateRole(requestDto);
        return ResponseEntity.ok(ResponseDto.of(SuccessCode.OK));
    }

    // 강제 탈퇴
    @PutMapping("updateIsForced/{uuid}")
    public ResponseEntity<?> updateIsForced(@PathVariable String uuid) {
        authService.updateIsForced(uuid);
        return ResponseEntity.ok(ResponseDto.of(SuccessCode.OK));
    }

}