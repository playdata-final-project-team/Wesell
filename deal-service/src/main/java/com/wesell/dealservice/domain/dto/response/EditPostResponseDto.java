package com.wesell.dealservice.domain.dto.response;

import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPostResponseDto {

    @NotBlank
    private Long category;

    @NotBlank
    private String title;

    @NotNull
    private Long price;

    @NotBlank
    private String detail;

    public EditPostResponseDto(DealPost post) {
        category = post.getCategory().getId();
        title = post.getTitle();
        price = post.getPrice();
        detail = post.getDetail();
    }
}
