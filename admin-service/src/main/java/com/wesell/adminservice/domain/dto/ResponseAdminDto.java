package com.wesell.adminservice.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAdminDto {

    private String jsVersion;
    private String cssVersion;
    private String title;
}
