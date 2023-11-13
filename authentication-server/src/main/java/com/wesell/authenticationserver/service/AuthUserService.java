package com.wesell.authenticationserver.service;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.repository.AuthUserRepository;
import com.wesell.authenticationserver.dto.request.CreateUserRequestDto;
import com.wesell.authenticationserver.dto.response.CreateUserFeignResponseDto;
import com.wesell.authenticationserver.global.util.Converter;
import com.wesell.authenticationserver.service.feign.UserServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthUserService {

    private final UserServiceFeignClient userServiceFeignClient;
    private final AuthUserRepository authUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Converter converter;

    /**
     * 회원 가입 기능
     */
    public void createUser(CreateUserRequestDto createUserRequestDto){
        log.debug("회원 가입 시작");

        log.debug("uuid 생성, 비밀번호 암호화, 회원 인증 정보 엔티티로 convert");
        String pw = passwordEncoder.encode(createUserRequestDto.getPw()); // 암호화
        String uuid = UUID.randomUUID().toString();
        createUserRequestDto.setUuid(uuid);
        createUserRequestDto.setPw(pw);
        AuthUser user = converter.toEntity(createUserRequestDto);

        log.debug("회원 인증 정보 - DB 저장");
        authUserRepository.save(user);

        // 연동 전 테스트를 위해 주석처리
        log.debug("User-Service Api Call - 회원가입 요청");
//        CreateUserFeignResponseDto feignDto = converter.toFeignDto(createUserRequestDto);
//        userServiceFeignClient.registerUserDetailInfo(feignDto);
    }
}
