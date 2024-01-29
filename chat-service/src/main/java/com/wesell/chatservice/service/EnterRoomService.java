package com.wesell.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnterRoomService {

    private final SimpMessagingTemplate template;

    public void enterRoom(String type, Long roomId, Long userId){

    }
}
