package com.wesell.dealservice.dto.request;

import com.wesell.dealservice.domain.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequestDto {

    @NotBlank
    private String value;

    public Category toEntity() {
        return Category.builder()
                .value(this.value)
                .build();
    }

}
