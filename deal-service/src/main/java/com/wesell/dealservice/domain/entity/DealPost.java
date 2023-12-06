package com.wesell.dealservice.domain.entity;

import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id", referencedColumnName = "c_value")
    private Category category;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "p_title", nullable = false)
    private String title;

    @Column(name = "p_price", nullable = false)
    private Long price;

    @Column(name = "p_link", nullable = false)
    private String link;

    @Column(name = "p_detail", nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "p_status", nullable = false)
    private SaleStatus status;

    @Column(name = "p_create_at")
    String createdAt;

    @Column(name = "isDeleted", nullable = false)
    Boolean isDeleted;

    @Builder
    public DealPost(String uuid, Category category  , String title, Long price,
                    String link, String detail, String createdAt) {
        this.uuid = uuid;
        this.category = category;
        this.title = title;
        this.price = price;
        this.link = link;
        this.detail = detail;
        this.status = SaleStatus.IN_PROGRESS;
        this.createdAt = createdAt;
        this.isDeleted = false;
    }

    public void editPost(EditPostRequestDto dto) {
        this.title = dto.getTitle();
        this.price = dto.getPrice();
        this.link = dto.getLink();
        this.detail = dto.getDetail();
    }

    public void editCategory(Category category) {
        this.category = category;
    }

    public void changeStatus() {
        this.status = SaleStatus.COMPLETED;
    }

    public void deleteMyPost() {
        this.isDeleted = true;
    }

}