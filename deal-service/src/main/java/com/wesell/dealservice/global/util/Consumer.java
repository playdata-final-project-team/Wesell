package com.wesell.dealservice.global.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.dealservice.domain.dto.request.ChangePostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.service.DealServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final ObjectMapper objectMapper;
    private final DealServiceImpl dealService;

    @RabbitListener(queues = "DEAL_CREATE_QUEUE")
    public void saveUrl(String message) throws IOException {

        String[] str = message.trim().substring(1, message.length()-1).split(",");

        String[] answer = str[str.length-1].split(":");

        if(answer[1].equals("1")) {
            UploadDealPostRequestDto dto = objectMapper.readValue(message, UploadDealPostRequestDto.class);

            dealService.createDealPost(dto);
        }
        else if(answer[1].equals("2")) {
            EditPostRequestDto dto = objectMapper.readValue(message, EditPostRequestDto.class);

            dealService.editPost(dto);
        }
        else if(answer[1].equals("3")) {
            ChangePostRequestDto dto = objectMapper.readValue(message, ChangePostRequestDto.class);

            dealService.changePostStatus(dto);
        }
    }

}