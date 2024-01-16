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
public class ChatMessageRequestDto {
    @NotBlank(message = "NullPointer Error - 작성자")
    private String sender;
    @NotBlank(message = "NullPointer Error - 내용")
    private String message;
}
