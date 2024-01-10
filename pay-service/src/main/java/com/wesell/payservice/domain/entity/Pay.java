package com.wesell.payservice.domain.entity;

import com.wesell.payservice.domain.enumerate.PayType;
import com.wesell.payservice.domain.enumerate.ShippingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "p_order", nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "p_type", nullable = false)
    private PayType type;

    @Column(name = "p_amount", nullable = false)
    private Long amount;

    @Column(name = "p_add", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "p_shipping", nullable = false)
    private ShippingStatus status;

    @Column(name = "p_shipNum", nullable = false)
    private Integer shippingNumber;

    @Column(name = "p_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Pay(String uuid, String orderNumber, Long amount,
                    String address, Integer shippingNumber, LocalDateTime createdAt) {
        this.uuid = uuid;
        this.orderNumber = orderNumber;
        this.type = PayType.ASSURED;
        this.amount = amount;
        this.address = address;
        this.status = ShippingStatus.PREPARING;
        this.shippingNumber = shippingNumber;
        this.createdAt = createdAt;
    }

}
