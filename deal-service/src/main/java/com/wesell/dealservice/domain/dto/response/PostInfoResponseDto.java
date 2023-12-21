package com.wesell.dealservice.domain.dto.response;

import com.wesell.dealservice.domain.entity.DealPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoResponseDto {

    private Long postId;
    private Long categoryId;
    private String title;
    private String createdAt;
    private Long price;
    private String detail;
    private String link;
    private String nickname;
    private String imageUrl;

    public PostInfoResponseDto (DealPost post, String nickname, String imageUrl) {
        this.postId = post.getId();
        this.categoryId = post.getCategory().getId();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.price = post.getPrice();
        this.detail = post.getDetail();
        this.link = post.getLink();
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

}