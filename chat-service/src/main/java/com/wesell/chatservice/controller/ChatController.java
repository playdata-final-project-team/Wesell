package com.wesell.chatservice.controller;

import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.dto.response.ChatMessageResponseDto;
import com.wesell.chatservice.global.util.DateFormatUtil;
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
    private final DateFormatUtil dateFormatUtil;

    @MessageMapping("rooms/{roomId}/send") // /pub/rooms/{roomId}/send로 들어오는 메시지들 처리
    @SendTo("/sub/{roomId}")
    public ChatMessageResponseDto sendMessage(@DestinationVariable String roomId, @Valid ChatMessageRequestDto requestDto){
        ChatMessageCreateCommand chatMessageCreateCommand =
                new ChatMessageCreateCommand(roomId, requestDto.getMessage(),requestDto.getSender());
        ChatMessage message = chatMessageCreateService.createChatMessage(chatMessageCreateCommand);
        ChatMessageResponseDto chatMessageResponseDto = ChatMessageResponseDto.builder()
                .message(message.getContent())
                .sender(message.getSender())
                .sendDate(dateFormatUtil.formatSendDate(message.getSendDate()))
                .build();
        return chatMessageResponseDto;
    }
}
