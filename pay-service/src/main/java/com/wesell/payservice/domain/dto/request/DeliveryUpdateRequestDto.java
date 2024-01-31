package com.wesell.payservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryUpdateRequestDto {
    @NotNull
    private Long id;
    @NotNull
    private Long version;
    @NotBlank
    private String receiver;
    @NotBlank
    private String address;

}
