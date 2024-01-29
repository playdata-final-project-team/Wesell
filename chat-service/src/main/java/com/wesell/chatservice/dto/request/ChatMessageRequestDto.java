package com.wesell.chatservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  ChatMessageRequestDto {
    private String roomId;
    private String sender;
    private String message;
    private String sendDate;
}
