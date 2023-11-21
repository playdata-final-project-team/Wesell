package com.wesell.adminservice.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SiteConfigRequestDto {

    private String jsVersion;
    private String cssVersion;
    private String title;
}
