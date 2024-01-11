package com.wesell.payservice.domain.entity;

import com.wesell.payservice.domain.dto.request.RequestPayDto;
import com.wesell.payservice.enumerate.PayType;
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

    //구매자 uuid
    @Column(name = "buyer", nullable = false)
    private String buyer;

    @Column(name = "productId", nullable = false)
    private Long productId;

    @Column(name = "d_order", nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "p_type", nullable = false)
    private PayType type;

    @Column(name = "p_amount", nullable = false)
    private Long amount;

    @Column(name = "p_add", nullable = false)
    private String address;

    @Column(name = "p_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Pay(String buyer, Long productId, String orderNumber, Long amount,
                    String address, PayType type , LocalDateTime createdAt) {
        this.buyer = buyer;
        this.productId = productId;
        this.orderNumber = orderNumber;
        this.type = type;
        this.amount = amount;
        this.address = address;
        this.createdAt = createdAt;
    }

    //비즈니스 로직
    public static Pay createPay(RequestPayDto requestDto, String orderNumber) {
        PayType type = PayType.ASSURED;

        if(PayType.UNASSURED.getCode() == requestDto.getType()) {
            type = PayType.UNASSURED;
        }

        Pay pay = Pay.builder()
                .buyer(requestDto.getBuyer())
                .productId(requestDto.getProductId())
                .orderNumber(orderNumber)
                .type(type)
                .amount(requestDto.getAmount())
                .address(requestDto.getAddress())
                .createdAt(LocalDateTime.now())
                .build();

        return pay;
    }

}
