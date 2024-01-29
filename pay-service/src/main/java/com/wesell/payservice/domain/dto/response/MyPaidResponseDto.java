package com.wesell.payservice.domain.dto.response;

import com.wesell.payservice.enumerate.ShippingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyPaidResponseDto {
    @NotBlank
    private Long payId;
    @NotNull
    private String title;
    @NotNull
    private String createdAt;
    @NotNull
    private ShippingStatus status;

}
