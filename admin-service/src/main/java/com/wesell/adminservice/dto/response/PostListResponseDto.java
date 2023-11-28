package com.wesell.adminservice.dto.response;

import com.wesell.adminservice.domain.enum_.SaleStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponseDto {
    private String title;
    private LocalDate createdAt;
    private SaleStatus status;
}
