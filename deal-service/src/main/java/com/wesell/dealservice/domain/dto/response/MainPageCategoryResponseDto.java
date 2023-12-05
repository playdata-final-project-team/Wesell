package com.wesell.dealservice.domain.dto.response;

import com.wesell.dealservice.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageCategoryResponseDto {
    private String value;

    public MainPageCategoryResponseDto(Category category) {
        this.value = category.getValue();
    }
}
