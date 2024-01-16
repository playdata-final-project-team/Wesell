package com.wesell.chatservice.controller;

import com.wesell.chatservice.domain.service.ChatMessageLoadService;
import com.wesell.chatservice.dto.query.ChatMessageListQuery;
import com.wesell.chatservice.global.response.success.SuccessApiResponse;
import com.wesell.chatservice.global.response.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/rooms")
public class ChatMessageController {

    private final ChatMessageLoadService messageLoadService;

    @GetMapping("{roomId}/messages")
    public ResponseEntity<?> getMessageList(@PathVariable Long roomId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size){
        ChatMessageListQuery chatMessageListQuery =  ChatMessageListQuery.builder()
                .roomId(roomId)
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(SuccessApiResponse.of(
                SuccessCode.OK,
                messageLoadService.getChatMessageList(chatMessageListQuery)
                )
        );
    }
}
