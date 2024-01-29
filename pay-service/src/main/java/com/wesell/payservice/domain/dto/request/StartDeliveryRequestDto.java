package com.wesell.payservice.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartDeliveryRequestDto {
    @NotNull
    private Long deliveryId;
    @NotNull
    private Long version;
    @NotNull
    private Integer shippingNumber;
}
