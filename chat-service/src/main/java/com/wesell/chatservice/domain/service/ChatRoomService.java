package com.wesell.chatservice.domain.service;

import com.wesell.chatservice.dto.request.ChatRoomListRequestDto;
import com.wesell.chatservice.dto.request.ChatRoomRequestDto;
import com.wesell.chatservice.dto.response.ChatRoomListResponseDto;
import com.wesell.chatservice.dto.response.ChatRoomResponseDto;
import org.springframework.data.redis.listener.ChannelTopic;

public interface ChatRoomService {

    String createChatRoom(ChatRoomRequestDto requestDto);

    ChatRoomListResponseDto myChatRoomList(String consumer, int page, int size);

    ChatRoomResponseDto findChatRoom(String roomId);

    void exitChatRoom(String roomId, String demander);

    String getTopic(String roomId);
}
