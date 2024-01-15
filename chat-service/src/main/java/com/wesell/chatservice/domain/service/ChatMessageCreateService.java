package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.dto.command.ChatMessageCreateCommand;

public interface ChatMessageCreateService {

    Long createChatMessage(ChatMessageCreateCommand command);
}
