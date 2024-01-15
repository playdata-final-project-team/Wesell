package com.wesell.chatservice.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatRoomCreateCommand {
    private String consumer;

    private Long productId;

    private String seller;
}
