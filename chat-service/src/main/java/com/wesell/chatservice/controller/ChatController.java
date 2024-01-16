package com.wesell.chatservice.controller;

import com.wesell.chatservice.dto.response.ChatMessageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.wesell.chatservice.dto.request.ChatMessageRequestDto;
import com.wesell.chatservice.domain.service.ChatMessageCreateService;
import com.wesell.chatservice.dto.command.ChatMessageCreateCommand;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageCreateService chatMessageCreateService;

    @MessageMapping("rooms/{roomId}/send")
    @SendTo("/sub/{roomId}")
    public ChatMessageResponseDto sendMessage(@DestinationVariable Long roomId, @Valid ChatMessageRequestDto requestDto){
        ChatMessageCreateCommand chatMessageCreateCommand =
                new ChatMessageCreateCommand(roomId, requestDto.getMessage(),requestDto.getSender());
        Long chatId = chatMessageCreateService.createChatMessage(chatMessageCreateCommand);
        ChatMessageResponseDto chatMessageResponseDto = ChatMessageResponseDto.builder()
                .id(chatId)
                .message(requestDto.getMessage())
                .sender(requestDto.getSender())
                .build();
        return chatMessageResponseDto;
    }

//    @MessageExceptionHandler
//    public String exception(){
//        return "Error has occurred";
//    }
}
