package com.wesell.dealservice.domain.dto.response;

import com.wesell.dealservice.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageCategoryResponseDto {
    private Long id;
    private String value;

    public MainPageCategoryResponseDto(Category category) {
        this.id = category.getId();
        this.value = category.getValue();
    }
}
