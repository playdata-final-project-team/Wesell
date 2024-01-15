package com.wesell.dealservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePostRequestDto {

    private String uuid;
    private Long postId;
    private int division = 3;
}
