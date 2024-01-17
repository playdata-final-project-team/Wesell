package com.wesell.dealservice.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductInfoFacadeDto {
    ProductInfoResponseDto productInfoResponseDto;
    UserFeignResponseDto userFeignResponseDto;
    String imageUrl;
}
