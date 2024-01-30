package com.wesell.dealservice.controller;

import com.wesell.dealservice.domain.dto.request.CreateCategoryRequestDto;
import com.wesell.dealservice.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2")
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @PostMapping("category")
    public ResponseEntity<?> createDealPost(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        categoryServiceImpl.createCategory(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("categories")
    public ResponseEntity<?> getCategories() {
        return new ResponseEntity<>(categoryServiceImpl.getCategoryList(), HttpStatus.OK);
    }

}
