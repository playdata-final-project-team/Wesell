package com.wesell.payservice.domain.entity;

import com.wesell.payservice.domain.dto.request.RequestDeliveryDto;
import com.wesell.payservice.enumerate.ShippingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "d_shipping", nullable = false)
    private ShippingStatus status;

    @Column(name = "d_shipNum")
    private Integer shippingNumber;

    //수령인
    @Column(name = "d_receiver", nullable = false)
    private String receiver;

    @Column(name = "d_address", nullable = false)
    private String address;

    @Column(name = "d_createdAt")
    private LocalDate createdAt;

    @Builder
    public Delivery(ShippingStatus status, Integer shippingNumber,
                    String receiver, String address, LocalDate createdAt) {
        this.status = status;
        this.shippingNumber = shippingNumber;
        this.receiver = receiver;
        this.address = address;
        this.createdAt = createdAt;
    }

    //비즈니스 로직
    public static Delivery createDelivery(RequestDeliveryDto requestDto) {
        Delivery delivery = Delivery.builder()
                .status(ShippingStatus.PREPARING)
                .shippingNumber(0)
                .receiver(requestDto.getReceiver())
                .address(requestDto.getAddress())
                .createdAt(LocalDate.now())
                .build();
        return delivery;
    }

    public void startDelivery(Integer shippingNumber){
        this.shippingNumber = shippingNumber;
        this.status = ShippingStatus.SHIPPING;
    }

    public void finishDelivery() {
        this.status = ShippingStatus.COMPLETE;
    }
}
