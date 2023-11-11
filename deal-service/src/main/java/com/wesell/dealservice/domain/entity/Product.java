package com.wesell.dealservice.domain.entity;

import com.wesell.dealservice.domain.SaleStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id", nullable = false)
    private String id;
    @Column(nullable = false)
    private String uuid;
    @Column(nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    @Column(nullable = false)
    private Long i_id;
    @Column(name = "p_title", nullable = false)
    private String title;
    @Column(name = "p_link", nullable = false)
    private String link;
    @Column(name = "p_detail", nullable = false)
    private String detail;
    @Column(name = "p_status", nullable = false)
    private SaleStatus status;

    public Product(String id, String userId, Category category, String title) {

    }
}
