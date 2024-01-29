package com.wesell.chatservice.controller;

import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.domain.service.ChatMessageService;
import com.wesell.chatservice.domain.service.ChatRoomService;
import com.wesell.chatservice.dto.response.ChatMessageResponseDto;
import com.wesell.chatservice.global.util.DateFormatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import com.wesell.chatservice.dto.request.ChatMessageRequestDto;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ChatController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final DateFormatUtil dateFormatUtil;

    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessageRequestDto requestDto) {
        ChatMessage message = chatMessageService.createChatMessage(requestDto);

        ChatMessageResponseDto response = ChatMessageResponseDto.builder()
                .roomId(message.getChatRoom().getId())
                .sender(message.getSender())
                .content(message.getContent())
                .sendDate(dateFormatUtil.formatSendDate(message.getSendDate()))
                .build();

        redisTemplate.convertAndSend(chatRoomService.getTopic(response.getRoomId()),response);
    }
}
