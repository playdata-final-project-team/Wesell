package com.wesell.adminservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteConfigResponseDto {

    private String jsVersion;
    private String cssVersion;
    private String title;
}
