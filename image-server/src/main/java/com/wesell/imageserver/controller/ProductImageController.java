package com.wesell.imageserver.controller;

import com.wesell.imageserver.service.FileUploadService;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v2")
public class ProductImageController {
    private final FileUploadService fileUploadService;
    public ProductImageController (FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    //value = , consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    @PostMapping("upload")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "id") Long productId, @RequestPart(value = "file")MultipartFile file) throws IOException {
        fileUploadService.saveImageUrl(productId, fileUploadService.uploadAndGetUrl(file));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
