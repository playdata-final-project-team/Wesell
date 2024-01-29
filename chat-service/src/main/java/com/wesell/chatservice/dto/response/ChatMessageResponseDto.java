package com.wesell.chatservice.dto.response;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageResponseDto{

    private String roomId; // MESSAGE 프레임 보낼 때, destination 설정용
    private String sender;
    private String content;
    private String sendDate;
}
