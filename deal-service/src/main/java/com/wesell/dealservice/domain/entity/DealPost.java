package com.wesell.dealservice.domain.entity;

import com.wesell.dealservice.domain.SaleStatus;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Getter
@Table(name = "product")
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

    @Column(name = "p_detail", nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "p_status", nullable = false)
    private SaleStatus saleStatus;

    @Column(name = "p_create_at")
    LocalDateTime createdAt;

    @Column(name = "isDeleted", nullable = false)
    Boolean isDeleted;

    @Builder
    public DealPost(String uuid, Category category, String title, Long price,
                    String detail, LocalDateTime createdAt) {
        this.uuid = uuid;
        this.category = category;
        this.title = title;
        this.price = price;
        this.detail = detail;
        this.saleStatus = SaleStatus.IN_PROGRESS;
        this.createdAt = createdAt;
        this.isDeleted = false;
    }

    public void editPost(EditPostRequestDto dto) {
        this.title = dto.getTitle();
        this.price = dto.getPrice();
        this.detail = dto.getDetail();
    }

    public void editCategory(Category category) {
        this.category = category;
    }

    public void changeStatus() {
        if(this.saleStatus == SaleStatus.IN_PROGRESS) {
            this.saleStatus = SaleStatus.COMPLETED;
        }else{
            this.saleStatus = SaleStatus.IN_PROGRESS;
        }
    }

    public void deleteMyPost() {
        this.isDeleted = true;
    }

}