package com.wesell.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuitRoomService {
    private final SimpMessagingTemplate template;

    public void quitRoom(){

    }
}
