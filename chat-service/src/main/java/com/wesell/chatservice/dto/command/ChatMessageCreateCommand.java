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
    private Long roomId;

    private String content;

    private String sender;
}
