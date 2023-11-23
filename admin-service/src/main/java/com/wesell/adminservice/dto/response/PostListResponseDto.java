package com.wesell.adminservice.dto.response;

import com.wesell.adminservice.domain.enum_.SaleStatus;

import java.time.LocalDate;

public class PostListResponseDto {
    private String title;
    private LocalDate createdAt;
    private SaleStatus status;
}
