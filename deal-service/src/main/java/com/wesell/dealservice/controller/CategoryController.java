package com.wesell.dealservice.controller;

import com.wesell.dealservice.domain.dto.request.CreateCategoryRequestDto;
import com.wesell.dealservice.domain.dto.request.CreateDealPostRequestDto;
import com.wesell.dealservice.service.CategoryService;
import com.wesell.dealservice.service.DealServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
@CrossOrigin("http://127.0.0.1:5500")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("post")
    public ResponseEntity<?> createDealPost(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        categoryService.createCategory(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
