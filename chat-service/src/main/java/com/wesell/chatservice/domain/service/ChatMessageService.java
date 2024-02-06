package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.dto.request.ChatMessageListRequestDto;
import com.wesell.chatservice.dto.request.ChatMessageRequestDto;
import com.wesell.chatservice.dto.response.ChatMessageListResponseDto;
import com.wesell.chatservice.dto.response.ChatMessageResponseDto;

public interface ChatMessageService {
    ChatMessageResponseDto createChatMessage(ChatMessageRequestDto requestDto);
    ChatMessageListResponseDto getChatMessageList(String roomId, int page, int size);
}
