package com.wesell.dealservice.dto.response;

import com.wesell.dealservice.domain.entity.DealPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPagePostResponseDto {

    private String title;
    private Long price;

    public MainPagePostResponseDto(DealPost post) {
        this.title = post.getTitle();
        this.price = post.getPrice();
    }

}