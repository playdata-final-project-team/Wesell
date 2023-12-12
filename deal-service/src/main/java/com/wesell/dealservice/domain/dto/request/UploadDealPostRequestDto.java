package com.wesell.dealservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UploadDealPostRequestDto {

    private String uuid;
    @NotNull
    private Long categoryId;
    @NotBlank
    private String title;
    @NotNull
    private Long price;
    @NotBlank
    private String link;
    @NotBlank
    private String detail;
    private LocalDate createdAt;

}