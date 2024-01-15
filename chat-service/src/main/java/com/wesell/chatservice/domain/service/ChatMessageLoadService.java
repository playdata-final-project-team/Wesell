package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.dto.query.ChatMessageListQuery;
import com.wesell.chatservice.dto.response.ChatMessageResponse;

import java.util.List;

public interface ChatMessageLoadService {
    List<ChatMessageResponse> getChatMessageList(ChatMessageListQuery query);
}
