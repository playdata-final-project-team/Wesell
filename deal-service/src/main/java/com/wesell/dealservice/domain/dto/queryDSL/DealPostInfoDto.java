package com.wesell.dealservice.domain.dto.queryDSL;

import com.querydsl.core.annotations.QueryProjection;
import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.entity.Category;
import lombok.Data;

@Data
public class DealPostInfoDto {

    private Long id;
    private Category category;
    private String uuid;
    private String title;
    private Long price;
    private String link;
    private String detail;
    private SaleStatus status;
    private String createdAt;
    private Boolean isDeleted;

    @QueryProjection
    public DealPostInfoDto(Long id, Category category, String uuid, String title,
                           Long price, String detail, SaleStatus status, String createdAt, Boolean isDeleted){
        this.id = id;
        this.category = category;
        this.uuid = uuid;
        this.title = title;
        this.price = price;
        this.detail = detail;
        this.status = status;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }
}
