package com.wesell.chatservice.service.message_broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.chatservice.dto.response.ChatMessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    // 메시지 브로커 -> 메시지를 전달받아 구독자들에게 발행하는 구현체
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatMessageResponseDto roomMessage = objectMapper.readValue(publishMessage, ChatMessageResponseDto.class);
            messagingTemplate.convertAndSend("/sub/chat/"+roomMessage.getRoomId(), roomMessage);
        }catch(Exception e){
            log.error("RedisSubscriber :  {}",e.getMessage());
        }
    }
}
