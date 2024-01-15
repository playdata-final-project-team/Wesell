package com.wesell.payservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeliveryDto {
    @NotBlank
    private String receiver;
    @NotBlank
    private String address;
}
