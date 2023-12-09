package com.wesell.userservice.dto.feigndto;

import com.wesell.userservice.error.exception.SuccessCode;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SignUpResponseDto {
    private String timeStamp;
    private String status;
    private String code;
    private String message;
    private List<String> vfMessages;

    private SignUpResponseDto(SuccessCode code) {
        this.timeStamp = LocalDateTime.now().toString();
        this.status = code.getStatus().toString();
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public static SignUpResponseDto of(SuccessCode code) {
        return new SignUpResponseDto(code);
    }
}
