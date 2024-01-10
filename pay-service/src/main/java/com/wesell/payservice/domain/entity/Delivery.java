package com.wesell.payservice.domain.entity;

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

@Entity
@Getter
@Table(name = "delivery")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "d_order", nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "d_shipping", nullable = false)
    private ShippingStatus status;

    @Column(name = "d_shipNum", nullable = false)
    private Integer shippingNumber;

    @Column(name = "p_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Delivery(String uuid, String orderNumber, Integer shippingNumber, LocalDateTime createdAt) {
      this.uuid = uuid;
      this.orderNumber = orderNumber;
      this.status = ShippingStatus.PREPARING;
      this.shippingNumber = shippingNumber;
      this.createdAt = createdAt;
    }
}
