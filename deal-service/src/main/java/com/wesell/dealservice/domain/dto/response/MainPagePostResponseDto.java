package com.wesell.dealservice.domain.dto.response;

import com.wesell.dealservice.domain.entity.DealPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPagePostResponseDto {

    private Long postId;
    private Long category;
    private String imageUrl;
    private String title;
    private Long price;

    public MainPagePostResponseDto(DealPost post, Image image) {
        this.postId = post.getId();
        this.category = post.getCategory().getId();
        this.imageUrl = image.getImageUrl();
        this.title = post.getTitle();
        this.price = post.getPrice();
    }

}