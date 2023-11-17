package com.wesell.dealservice.controller;

import com.wesell.dealservice.dto.request.CreateCategoryRequestDto;
import com.wesell.dealservice.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("deal-service")
//@CrossOrigin("http://127.0.0.1:5500")
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @PostMapping("category")
    public ResponseEntity<?> createDealPost(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        categoryServiceImpl.createCategory(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
