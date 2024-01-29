package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.dto.query.ChatRoomListQuery;
import com.wesell.chatservice.dto.response.ChatRoomListResponseDto;

public interface ChatRoomLoadService {
    ChatRoomListResponseDto getChatRoomList(ChatRoomListQuery query);
}
