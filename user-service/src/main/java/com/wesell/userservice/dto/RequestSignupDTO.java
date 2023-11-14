package com.wesell.userservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestSignupDTO {
    private String name;
    private String nickname;
    private String phone;
    private String uuid; //인증서버와 유저서비스서버가 서로 이메일 정보가 들어가 있는지
    // 확인하기 위해서 회원구분번호로 저장을 해서 서로 확인을 함
    private boolean agree;
}