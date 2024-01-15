package com.wesell.chatservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomResponse {
    private Long roomId;
    private String seller;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ChatMessageResponse> chatMessageList;
}
