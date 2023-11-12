package com.wesell.dealservice.service;

import com.wesell.dealservice.domain.dto.request.RequestCreateDealPostDto;

public interface DealService {
    void createDealPost(RequestCreateDealPostDto requestCreatePostDto);
}
