package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.dto.command.ChatRoomCreateCommand;

public interface ChatRoomCreateService {
    Long createChatRoom(ChatRoomCreateCommand command);
}
