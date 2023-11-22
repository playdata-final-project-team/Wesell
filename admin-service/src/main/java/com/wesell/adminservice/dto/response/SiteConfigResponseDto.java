package com.wesell.adminservice.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteConfigResponseDto {

    private String jsVersion;
    private String cssVersion;
    private String title;

    public SiteConfigResponseDto(String s) {
    }
}
