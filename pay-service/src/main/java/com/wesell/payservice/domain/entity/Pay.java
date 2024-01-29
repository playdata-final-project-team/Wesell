package com.wesell.payservice.domain.entity;

import com.wesell.payservice.domain.dto.request.PayRequestDto;
import com.wesell.payservice.enumerate.PayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Lock;

@Entity @Getter
@Table(name = "pay")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Long id;

    @Column(name = "d_id")
    private Long deliveryId;

    //구매자 uuid
    @Column(name = "d_buyer_uuid", nullable = false)
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

    @Column(name = "p_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "p_isDeleted",  nullable = false)
    private Boolean isDeleted;

    @Builder
    public Pay( Long deliveryId, String buyer, Long productId, String orderNumber,
               Long amount, PayType type , LocalDateTime createdAt, Boolean isDeleted) {
        this.deliveryId = deliveryId;
        this.buyer = buyer;
        this.productId = productId;
        this.orderNumber = orderNumber;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    //비즈니스 로직
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public static Pay createPay(PayRequestDto requestDto, String orderNumber, Long amount) {
        PayType type = PayType.UNASSURED;

        //안전결제 버튼을 누른 경우
        if(PayType.ASSURED.getCode() == requestDto.getType()) {
            type = PayType.ASSURED;
        }

        Pay pay = Pay.builder()
                .deliveryId(requestDto.getDeliveryId())
                .buyer(requestDto.getBuyer())
                .productId(requestDto.getProductId())
                .orderNumber(orderNumber)
                .type(type)
                .amount(amount)
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
        return pay;
    }

    public void deleteMyPay() {
        this.isDeleted = true;
    }
}
