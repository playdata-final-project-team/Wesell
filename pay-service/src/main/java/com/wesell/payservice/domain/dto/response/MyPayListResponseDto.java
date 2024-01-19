package com.wesell.payservice.domain.dto.response;

import com.wesell.payservice.domain.entity.Delivery;
import com.wesell.payservice.domain.entity.Pay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPayListResponseDto {
    @NotNull
    private Long payId;
    @NotNull
    private Long deliveryId;
    @NotBlank
    private String title;
    @NotBlank
    private LocalDate date;
    @NotBlank
    private String status;

    public MyPayListResponseDto(Pay pay, String title, Delivery delivery) {
        this.payId = pay.getId();
        this.deliveryId = pay.getDeliveryId();
        this.title = title;
        this.date = pay.getCreatedAt().toLocalDate();
        this.status = delivery.getStatus().getName();
    }
}
