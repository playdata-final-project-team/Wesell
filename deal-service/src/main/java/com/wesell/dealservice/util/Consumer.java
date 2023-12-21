package com.wesell.dealservice.util;

import com.amazonaws.services.s3.transfer.Upload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.dealservice.domain.dto.request.ChangePostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.UploadFileRequestDto;
import com.wesell.dealservice.domain.dto.response.EditPostResponseDto;
import com.wesell.dealservice.service.DealMessageQueueService;
import com.wesell.dealservice.service.DealService;
import com.wesell.dealservice.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class Consumer {

    private final ObjectMapper objectMapper;
    private final DealMessageQueueService dealService;
    private final FileUploadService fileUploadService;


    @RabbitListener(queues = "DEAL_CREATE_QUEUE")
    public void createDeal(String message) throws JsonProcessingException {
        // objectMapper.readValue("String형식인 JSON", "목적객체.class);
        UploadDealPostRequestDto dto = objectMapper.readValue(message, UploadDealPostRequestDto.class);
        // 서비스에서 DTO를 입력받아 DB에 INSERT해주는 로직을 호출
        dealService.createDealPost(dto);
    }

    @RabbitListener(queues = "DEAL_CREATE_QUEUE")
    public void saveUrl(String message) throws IOException {

        UploadFileRequestDto dto = objectMapper.readValue(message, UploadFileRequestDto.class);

        fileUploadService.saveImageUrl(dto);
    }

    @RabbitListener(queues = "DEAL_UPDATE_QUEUE")
    public EditPostResponseDto editPostInfo(String message) throws JsonProcessingException {

        EditPostRequestDto dto = objectMapper.readValue(message, EditPostRequestDto.class);
        return dealService.editPost(dto);
    }

    @RabbitListener(queues = "DEAL_UPDATE_QUEUE")
    public void changeStatus(String message) throws JsonProcessingException {

        ChangePostRequestDto dto = objectMapper.readValue(message, ChangePostRequestDto.class);
        dealService.changePostStatus(dto);
    }


}