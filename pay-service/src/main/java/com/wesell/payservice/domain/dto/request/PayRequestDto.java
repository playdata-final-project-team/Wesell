package com.wesell.payservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayRequestDto {
    @NotNull
    private Long deliveryId;
    @NotBlank
    private String buyer;
    @NotNull
    private Long productId;
    @NotNull
    private Integer type;
}
