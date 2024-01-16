package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.dto.query.ChatRoomListQuery;
import com.wesell.chatservice.dto.query.ChatRoomQuery;
import com.wesell.chatservice.dto.response.ChatRoomListResponseDto;
import com.wesell.chatservice.dto.response.ChatRoomResponseDto;

public interface ChatRoomLoadService {
    ChatRoomResponseDto getChatRoomById(ChatRoomQuery chatRoomQuery);
    ChatRoomListResponseDto getChatRoomList(ChatRoomListQuery query);
}
