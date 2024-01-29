package com.wesell.chatservice.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatMessageCreateCommand {
    private String roomId;

    private String content;

    private String sender;
}
