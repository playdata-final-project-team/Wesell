package com.wesell.chatservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatMessageCreateService;
import com.wesell.chatservice.dto.command.ChatMessageCreateCommand;
import com.wesell.chatservice.exception.ChatRoomNotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ChatMessageCreateServiceImpl implements ChatMessageCreateService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Long createChatMessage(ChatMessageCreateCommand command) {

        log.debug("채팅 메시지 생성 시작");

        log.debug("채팅 방 번호로 조회 - 없으면 예외처리");
        ChatRoom chatRoom = chatRoomRepository.findById(command.getRoomId()).orElseThrow(
                () -> new ChatRoomNotFoundException()
        );

        log.debug("채팅 메시지 생성");
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(command.getSender())
                .content(command.getContent())
                .sendDate(LocalDateTime.now())
                .build();

        log.debug("Cascade 를 이용한 데이터 생성 및 저장");
        chatRoom.createMessage(chatMessage);
        chatRoomRepository.save(chatRoom);
        return chatMessage.getId();
    }
}
