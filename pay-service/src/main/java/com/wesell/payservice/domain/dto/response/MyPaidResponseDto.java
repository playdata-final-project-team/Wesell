package com.wesell.payservice.domain.dto.response;

import com.wesell.payservice.enumerate.ShippingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyPaidResponseDto {
    @NotNull
    private Long payId;
    @NotBlank
    private String title;
    @NotBlank
    private String createdAt;
    @NotBlank
    private ShippingStatus status;

}
