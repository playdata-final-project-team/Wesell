package com.wesell.payservice.domain.entity;

import com.wesell.payservice.enumerate.ShippingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@Table(name = "pay")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "p_order")
    private String orderNumber;

    @Column(name = "p_way")
    private String way;

    @Column(name = "p_amount")
    private Long amount;

    @Column(name = "p_add")
    private String address;

    @Column(name = "p_shipping")
    private ShippingStatus status;

    @Column(name = "p_shipNum")
    private Integer shippingNumber;

    @Column(name = "p_createdAt")
    private LocalDateTime createdAt;
}
