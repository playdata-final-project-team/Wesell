package com.wesell.dealservice.domain.dto.facade;

import com.wesell.dealservice.domain.dto.response.MainPageCategoryResponseDto;
import com.wesell.dealservice.domain.dto.response.MainPagePostResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MainPageInfoFacadeDto {

    private List<MainPageCategoryResponseDto> categoryDto;
    private List<MainPagePostResponseDto> postDto;

}
