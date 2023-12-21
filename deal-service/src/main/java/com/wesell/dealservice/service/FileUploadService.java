package com.wesell.dealservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.wesell.dealservice.domain.dto.request.UploadFileRequestDto;
import com.wesell.dealservice.domain.entity.Image;
import com.wesell.dealservice.domain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;


    public String getUrl(MultipartFile file) throws IOException {
        return uploadAndGetUrl(file);
    }

    public void saveImageUrl(UploadFileRequestDto requestDto) throws IOException {
        Image userImage = Image.builder()
                .postId(requestDto.getPostId())
                .imageUrl(requestDto.getUrl())
                .build();
        imageRepository.save(userImage);
    }

    public String uploadAndGetUrl(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

}