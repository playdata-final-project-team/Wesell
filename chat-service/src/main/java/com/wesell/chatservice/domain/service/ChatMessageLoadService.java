package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.dto.query.ChatMessageListQuery;
import com.wesell.chatservice.dto.response.ChatMessageResponseDto;

import java.util.List;

public interface ChatMessageLoadService {
    List<ChatMessageResponseDto> getChatMessageList(ChatMessageListQuery query);
}
