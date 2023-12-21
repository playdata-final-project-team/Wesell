package com.wesell.dealservice.domain.dto.response;

import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.entity.DealPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPostListResponseDto {
    private Long id;
    private String title;
    private String createdAt;
    private SaleStatus saleStatus;

    public MyPostListResponseDto(DealPost post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.saleStatus = post.getSaleStatus();
    }

}