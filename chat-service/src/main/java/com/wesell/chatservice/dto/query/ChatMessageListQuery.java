package com.wesell.chatservice.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageListQuery {
    private Long roomId;
    private int page;
    private int size;
}
