package com.wesell.boardservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private String uuid;

}
