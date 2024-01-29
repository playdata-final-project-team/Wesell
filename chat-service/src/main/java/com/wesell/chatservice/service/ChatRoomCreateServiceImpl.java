package com.wesell.chatservice.service;

import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatRoomCreateService;
import com.wesell.chatservice.dto.command.ChatRoomCreateCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomCreateServiceImpl implements ChatRoomCreateService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    @Override
    public String createChatRoom(ChatRoomCreateCommand command) {

        List<ChatRoom> list = chatRoomRepository
                .findALlByProductIdAndConsumer(command.getProductId(), command.getConsumer());

        if(!list.isEmpty()){
            ChatRoom chatRoom = list.get(0);
            return chatRoom.getId();
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .id(UUID.randomUUID().toString())
                .productId(command.getProductId().toString())
                .consumer(command.getConsumer())
                .seller(command.getSeller())
                .build();

        ChatRoom savedData = chatRoomRepository.save(chatRoom);
        return savedData.getId();
    }
}
