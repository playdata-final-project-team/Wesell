package com.wesell.dealservice.facade;

import com.wesell.dealservice.dto.facade.MainPageInfoFacadeDto;
import com.wesell.dealservice.service.CategoryServiceImpl;
import com.wesell.dealservice.service.DealServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainPageFacadeService {

    private final CategoryServiceImpl categoryService;
    private final DealServiceImpl dealService;

    public MainPageInfoFacadeDto getFacadeDto() {
        MainPageInfoFacadeDto dto = new MainPageInfoFacadeDto();
        dto.setCategoryDto(categoryService.getCategoryList());
        dto.setPostDto(dealService.getDealPostLists());

        return dto;
    }

}
