package com.wesell.chatservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.wesell.chatservice.dto.request.ChatMessageRequest;
import com.wesell.chatservice.dto.response.ChatMessageResponse;
import com.wesell.chatservice.domain.service.ChatMessageCreateService;
import com.wesell.chatservice.dto.command.ChatMessageCreateCommand;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageCreateService chatMessageCreateService;

    @MessageMapping("rooms/{roomId}/send")
    @SendTo("/sub/{roomId}")
    public ChatMessageResponse sendMessage(@DestinationVariable Long roomId,@Valid ChatMessageRequest requestDto){
        ChatMessageCreateCommand chatMessageCreateCommand =
                new ChatMessageCreateCommand(roomId, requestDto.getMessage(),requestDto.getSender());
        Long chatId = chatMessageCreateService.createChatMessage(chatMessageCreateCommand);
        ChatMessageResponse chatMessageResponse = ChatMessageResponse.builder()
                .id(chatId)
                .message(requestDto.getMessage())
                .sender(requestDto.getSender())
                .build();
        return chatMessageResponse;
    }

//    @MessageExceptionHandler
//    public String exception(){
//        return "Error has occurred";
//    }
}
