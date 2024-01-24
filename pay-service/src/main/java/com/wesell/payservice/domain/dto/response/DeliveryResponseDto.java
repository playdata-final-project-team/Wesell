package com.wesell.payservice.domain.dto.response;

import com.wesell.payservice.domain.entity.Delivery;
import com.wesell.payservice.enumerate.ShippingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDto {
    @NotBlank
    private Long id;
    @NotNull
    private String receiver;
    @NotNull
    private String address;
    @NotNull
    private String status;

    public DeliveryResponseDto(Delivery delivery) {
        this.id = delivery.getId();
        this.receiver = delivery.getReceiver();
        this.address = delivery.getAddress();
        this.status = ShippingStatus.PREPARING.getName();
    }
}
