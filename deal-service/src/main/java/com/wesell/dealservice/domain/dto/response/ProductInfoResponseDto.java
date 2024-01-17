package com.wesell.dealservice.domain.dto.response;

import com.wesell.dealservice.domain.entity.DealPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoResponseDto {

    private String uuid;
    private Long postId;
    private Long categoryId;
    private String title;
    private String createdAt;
    private Long price;
    private String detail;
    private String link;

    public ProductInfoResponseDto(DealPost post) {
        this.uuid = post.getUuid();
        this.postId = post.getId();
        this.categoryId = post.getCategory().getId();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.price = post.getPrice();
        this.detail = post.getDetail();
        this.link = post.getLink();
    }

}