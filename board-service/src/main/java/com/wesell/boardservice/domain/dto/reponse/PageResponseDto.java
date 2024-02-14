package com.wesell.boardservice.domain.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDto implements Serializable {
    private List<?> dtoList;
    private String boardTitle;
    private int page;
    private long totalElements;
    private int size;

    private long totalPages;
}
