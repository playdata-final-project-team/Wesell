package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.dto.request.ChatMessageListRequestDto;
import com.wesell.chatservice.dto.request.ChatMessageRequestDto;
import com.wesell.chatservice.dto.response.ChatMessageListResponseDto;

public interface ChatMessageService {
    ChatMessage createChatMessage(ChatMessageRequestDto requestDto);
    ChatMessageListResponseDto getChatMessageList(String roomId, int page, int size);
}
