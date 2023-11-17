package com.wesell.dealservice.service;

import com.wesell.dealservice.dto.request.CreateCategoryRequestDto;
import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.repository.CategoryRepository;
import com.wesell.dealservice.dto.response.MainPageCategoryResponseDto;
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

    @Override
    public void createCategory(CreateCategoryRequestDto requestDto) {
        Category category = requestDto.toEntity();
        categoryRepository.save(category);
    }

    @Override
    public List<MainPageCategoryResponseDto> getMainPageInfo() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(MainPageCategoryResponseDto::new).collect(Collectors.toList());
    }

}
