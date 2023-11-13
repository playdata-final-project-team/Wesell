package com.wesell.dealservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @Builder
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id", nullable = false)
    Long id;

    @Column(name = "c_value", nullable = false)
    String value;

}
