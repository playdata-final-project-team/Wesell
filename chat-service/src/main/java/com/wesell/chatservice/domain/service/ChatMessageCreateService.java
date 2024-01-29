package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.dto.command.ChatMessageCreateCommand;

public interface ChatMessageCreateService {

    ChatMessage createChatMessage(ChatMessageCreateCommand command);
}
