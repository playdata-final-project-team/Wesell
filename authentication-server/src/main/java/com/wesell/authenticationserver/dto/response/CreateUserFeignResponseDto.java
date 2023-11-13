package com.wesell.authenticationserver.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateUserFeignResponseDto {

    private String name; // 회원명

    private String nickname; // 닉네임

    private String phone; // 휴대전화 번호

    private boolean agree; // 개인정보 제공 동의여부

    private String uuid; // 회원 구분 번호
}
