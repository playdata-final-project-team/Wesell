package com.wesell.dealservice.dto.response;

import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.entity.DealPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPostListResponseDto {
    private String title;
    private LocalDate createdAt;
    private SaleStatus status;

    public MyPostListResponseDto(DealPost post) {
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        this.status = post.getStatus();
    }

}