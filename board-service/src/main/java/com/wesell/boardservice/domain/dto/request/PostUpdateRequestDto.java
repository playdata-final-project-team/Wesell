package com.wesell.boardservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUpdateRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
