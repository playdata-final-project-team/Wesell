package com.wesell.payservice.domain.dto.response;

import com.wesell.payservice.domain.entity.Pay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayDto {

    @NotNull
    private String buyer;
    @NotBlank
    private Long productId;
    @NotNull
    private String orderNumber;
    @NotBlank
    private Integer type;
    @NotBlank
    private Long amount;
    @NotNull
    private String address;

    public ResponsePayDto(Pay pay) {
        this.buyer = pay.getBuyer();
        this.productId = pay.getProductId();
        this.orderNumber = pay.getOrderNumber();
        this.type = pay.getType().getCode();
        this.amount = pay.getAmount();
        this.address = pay.getAddress();
    }

}
