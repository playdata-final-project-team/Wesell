package com.wesell.adminservice.domain.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteConfigResponseDto {

    private String jsVersion;
    private String cssVersion;
    private String title;
}
