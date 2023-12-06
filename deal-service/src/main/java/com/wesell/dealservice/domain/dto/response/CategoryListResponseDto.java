package com.wesell.dealservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListResponseDto {

    private List<MainPageCategoryResponseDto> categories;

}
