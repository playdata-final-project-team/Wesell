package com.wesell.boardservice.domain.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDto {
    private List<?> dtoList;
    private int page;
    private long totalElements;
    private int size;
}
