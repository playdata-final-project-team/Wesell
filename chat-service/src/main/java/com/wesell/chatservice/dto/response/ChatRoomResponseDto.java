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
public class ChatRoomResponseDto {
    private String roomId;
    private String seller;
    private String consumer;
    private boolean isAlive;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastSendDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastSendMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String imageUrl;
}
