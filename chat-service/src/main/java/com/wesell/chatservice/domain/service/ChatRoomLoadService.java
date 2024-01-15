package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.dto.query.ChatRoomListQuery;
import com.wesell.chatservice.dto.query.ChatRoomQuery;
import com.wesell.chatservice.dto.response.ChatRoomListResponse;
import com.wesell.chatservice.dto.response.ChatRoomResponse;

public interface ChatRoomLoadService {
    ChatRoomResponse getChatRoomById(ChatRoomQuery chatRoomQuery);
    ChatRoomListResponse getChatRoomList(ChatRoomListQuery query);
}
