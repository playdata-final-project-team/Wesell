package com.wesell.chatservice.service;

import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatRoomCreateService;
import com.wesell.chatservice.dto.command.ChatRoomCreateCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomCreateServiceImpl implements ChatRoomCreateService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    @Override
    public Long createChatRoom(ChatRoomCreateCommand command) {
        ChatRoom chatRoom = ChatRoom.builder()
                .productId(command.getProductId().toString())
                .consumer(command.getConsumer())
                .seller(command.getSeller())
                .build();
        ChatRoom savedData = chatRoomRepository.save(chatRoom);
        return savedData.getId();
    }
}
