package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.CreateCategoryRequestDto;
import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void createCategory(CreateCategoryRequestDto requestDto) {
        Category category = requestDto.toEntity();
        categoryRepository.save(category);
    }

}
