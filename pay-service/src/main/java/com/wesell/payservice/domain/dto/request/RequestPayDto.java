package com.wesell.payservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPayDto {
    @NotNull
    private String uuid;
    @NotBlank
    private Long productId;
    @NotBlank
    private Integer type;
    @NotNull
    private String address;

}
