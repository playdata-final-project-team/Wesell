package com.wesell.dealservice.domain.dto.request;

import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.entity.DealPost;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDealPostRequestDto {

    private String uuid;

    @NotBlank
    private String title;

    @NotBlank
    private Category category;

    @NotBlank
    private Long price;

    @NotBlank
    private String detail;

    @NotBlank
    private String link;

    public DealPost toEntity() {
        return DealPost.builder()
                .uuid(this.uuid)
                .title(this.title)
                .category(this.category)
                .price(this.price)
                .detail(this.detail)
                .link(this.link)
                .build();
    }

}
