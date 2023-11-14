package com.wesell.dealservice.domain.dto.response;

import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @NotNull
    private Long price;

    @NotBlank
    private String link;

    @NotBlank
    private String detail;

    public EditPostResponseDto(DealPost post) {
        category = post.getCategory();
        title = post.getTitle();
        price = post.getPrice();
        link = post.getLink();
        detail = post.getDetail();
    }
}
