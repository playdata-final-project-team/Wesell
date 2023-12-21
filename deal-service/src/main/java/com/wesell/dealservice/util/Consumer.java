package com.wesell.dealservice.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.dealservice.domain.dto.request.ChangePostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.UploadFileRequestDto;
import com.wesell.dealservice.service.DealMessageQueueService;
import com.wesell.dealservice.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;



@Component
@RequiredArgsConstructor
public class Consumer {

    private final ObjectMapper objectMapper;
    private final DealMessageQueueService dealService;
    private final FileUploadService fileUploadService;



    @RabbitListener(queues = "DEAL_CREATE_QUEUE")
    public void saveUrl(String message) throws IOException, JSONException {

        String[] str = message.trim().substring(1, message.length()-1).split(",");

        String[] answer = str[str.length-1].split(":");

        if(answer[1].equals("1")) {
            UploadDealPostRequestDto dto = objectMapper.readValue(message, UploadDealPostRequestDto.class);

            dealService.createDealPost(dto);
        }
        else if(answer[1].equals("2")) {
            UploadFileRequestDto dto = objectMapper.readValue(message, UploadFileRequestDto.class);

            fileUploadService.saveImageUrl(dto);
        }
        else if(answer[1].equals("3")) {
            EditPostRequestDto dto = objectMapper.readValue(message, EditPostRequestDto.class);

            dealService.editPost(dto);
        }
        else if(answer[1].equals("4")) {
            ChangePostRequestDto dto = objectMapper.readValue(message, ChangePostRequestDto.class);

            dealService.changePostStatus(dto);
        }
    }


}