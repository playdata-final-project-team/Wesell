package com.wesell.dealservice.service;

import com.wesell.dealservice.dto.request.CreateCategoryRequestDto;
import com.wesell.dealservice.dto.response.MainPageCategoryResponseDto;
import java.util.List;

public interface CategoryService {
    void createCategory(CreateCategoryRequestDto requestDto);
    List<MainPageCategoryResponseDto> getCategoryList();
}