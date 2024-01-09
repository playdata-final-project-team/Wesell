package org.wesell.chatservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.wesell.chatservice.domain.ChatMessage;

@RestController
@RequiredArgsConstructor
public class ChatController {

}
