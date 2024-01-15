package com.wesell.payservice.domain.dto.response;

import com.wesell.payservice.domain.entity.Pay;
import com.wesell.payservice.enumerate.ShippingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayDto {

    @NotBlank
    private Long productId;
    @NotNull
    private String orderNumber;
    @NotBlank
    private Long amount;


    public ResponsePayDto(Pay pay) {
        this.productId = pay.getProductId();
        this.orderNumber = pay.getOrderNumber();
        this.amount = pay.getAmount();
    }

}
