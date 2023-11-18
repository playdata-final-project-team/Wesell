package com.wesell.dealservice.dto;

import com.wesell.dealservice.dto.response.MainPageCategoryResponseDto;
import com.wesell.dealservice.dto.response.MainPagePostResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class FacadeDto {

    private List<MainPageCategoryResponseDto> categoryDto;
    private List<MainPagePostResponseDto> postDto;

}
