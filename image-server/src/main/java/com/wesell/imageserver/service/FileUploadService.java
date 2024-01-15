package com.wesell.imageserver.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.wesell.imageserver.domain.dto.request.UploadFileRequestDto;
import com.wesell.imageserver.domain.entity.ProductImage;
import com.wesell.imageserver.domain.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final ProductImageRepository productImageRepository;

    public FileUploadService (AmazonS3 amazonS3, ProductImageRepository productImageRepository) {
        this.amazonS3 = amazonS3;
        this.productImageRepository = productImageRepository;
    }

    public void saveImageUrl(Long productId, String url) {
        ProductImage userImage = ProductImage.builder()
                .productId(productId)
                .imageUrl(url)
                .build();
        productImageRepository.save(userImage);
    }

    public String uploadAndGetUrl(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

}