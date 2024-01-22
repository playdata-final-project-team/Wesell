package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.dto.command.ChatRoomCreateCommand;

public interface ChatRoomCreateService {
    String createChatRoom(ChatRoomCreateCommand command);
}
