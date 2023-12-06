package com.wesell.authenticationserver.controller.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateUserRequestDto { // 회원가입(front) -> Auth-Server

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 양식이 아닙니다.")
    private String email; // 이메일

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Size(min = 8,max = 16,message = "비밀번호를 8자 이상 16자 이하로 입력하세요.")
    private String pw; // 비밀번호

    @NotBlank(message = "비밀번호를 확인해주세요.")
    private String pwRe; // 비밀번호 확인

    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글로 작성해주세요.")
    @Size(min = 2, message= "이름은 2자 이상 입력하세요.")
    private String name; // 회원명

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern()
    @Size(min = 1, max = 15, message = "닉네임은 1자이상 15자 이하로 입력하세요.")
    private String nickname; // 닉네임

    @NotBlank(message = "휴대전화 번호를 입력해주세요.")
    @Pattern(regexp = "^01(0|1|[6-8])\\d{3,4}\\d{4}$", message="휴대전화 번호 양식이 아닙니다.")
    private String phone; // 휴대전화 번호

    @AssertTrue(message = "개인 정보 제공에 동의 해주세요.")
    private boolean agree; // 개인정보 제공 동의여부

    @JsonIgnore
    private String uuid; // 회원 구분 번호

}
