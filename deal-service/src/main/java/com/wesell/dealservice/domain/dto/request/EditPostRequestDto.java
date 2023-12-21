package com.wesell.dealservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPostRequestDto {

    private String uuid;

    @NotNull
    private Long categoryId;

    @NotBlank
    private String title;

    @NotNull
    private Long price;

    @NotBlank
    private String link;

    @NotBlank
    private String detail;

    private Long postId;
    private int division = 3;
}