package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.CreateCategoryRequestDto;
import com.wesell.dealservice.domain.dto.response.CategoryListResponseDto;

public interface CategoryService {
    void createCategory(CreateCategoryRequestDto requestDto);
    CategoryListResponseDto getCategoryList();
}