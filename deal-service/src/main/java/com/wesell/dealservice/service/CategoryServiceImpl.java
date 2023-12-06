package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.CreateCategoryRequestDto;
import com.wesell.dealservice.domain.dto.response.CategoryListResponseDto;
import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.repository.CategoryRepository;
import com.wesell.dealservice.domain.dto.response.MainPageCategoryResponseDto;
import com.wesell.dealservice.error.ErrorCode;
import com.wesell.dealservice.error.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    //카테고리 생성
    @Override
    public void createCategory(CreateCategoryRequestDto requestDto) {

        if (!categoryRepository.findAll().contains(requestDto.getValue())) {
            Category category = requestDto.toEntity();
            categoryRepository.save(category);
        } else {
            throw new CustomException(ErrorCode.DUPLICATED_REQUEST);
        }

    }

    //카테고리 리스트
    @Override
    public CategoryListResponseDto getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        CategoryListResponseDto responseDto = new CategoryListResponseDto();
        responseDto.setCategories(categories.stream()
                .map(MainPageCategoryResponseDto::new)
                .collect(Collectors.toList()));

        return responseDto;
    }

}
