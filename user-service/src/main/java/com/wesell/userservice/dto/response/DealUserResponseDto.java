package com.wesell.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealUserResponseDto {
    private String nickname;
    private Long dealCount;
    //todo : 리뷰 추가
}
