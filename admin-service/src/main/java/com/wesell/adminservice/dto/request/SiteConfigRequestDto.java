package com.wesell.adminservice.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SiteConfigRequestDto {

    private String jsVersion;
    private String cssVersion;
    private String title;
}
