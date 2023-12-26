package com.wesell.dealservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UploadDealPostRequestDto {

    private String uuid;
    @NotNull
    private String categoryId;
    @NotBlank
    private String title;
    @NotNull
    private String  price;
    @NotBlank
    private String link;
    @NotBlank
    private String detail;

}