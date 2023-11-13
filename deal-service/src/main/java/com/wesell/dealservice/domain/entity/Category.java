package com.wesell.dealservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @Builder
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    Long id;

    @Column(name = "c_value", nullable = false)
    String value;

    @OneToMany(mappedBy = "category")
    private List<DealPost> products = new ArrayList<>();

    @Builder
    public Category(Long id, String value) {
        this.id = id;
        this.value = value;
    }

}