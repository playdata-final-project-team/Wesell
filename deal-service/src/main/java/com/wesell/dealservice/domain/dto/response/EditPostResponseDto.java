package com.wesell.dealservice.domain.dto.response;

import com.wesell.dealservice.domain.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPostResponseDto {

    @NotBlank
    private Category category;

    @NotBlank
    private String title;

    @NotBlank
    private Long price;

    @NotBlank
    private String link;

    @NotBlank
    private String detail;

}
