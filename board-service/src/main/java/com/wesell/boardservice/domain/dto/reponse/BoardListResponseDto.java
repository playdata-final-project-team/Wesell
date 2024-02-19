package com.wesell.boardservice.domain.dto.reponse;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardListResponseDto {
    private Long id;
    private String title;
}
