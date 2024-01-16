package com.wesell.chatservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomListResponseDto {
    private List<ChatRoomResponseDto> roomList;
    private boolean hasNext;
}
