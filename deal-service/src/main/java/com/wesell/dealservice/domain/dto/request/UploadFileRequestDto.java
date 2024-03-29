package com.wesell.dealservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileRequestDto {

    private Long postId;
    private String url;
    private int division = 2;

    public UploadFileRequestDto(Long postId, String url) {
        this.postId = postId;
        this.url = url;
        this.division = 2;
    }
}
