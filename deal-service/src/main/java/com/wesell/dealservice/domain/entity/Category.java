package com.wesell.dealservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id", nullable = false)
    private Long id;

    @Column(name = "c_value", nullable = false)
    private String value;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public Category(Long id, String value) {
        this.id = id;
        this.value = value;
    }
}
