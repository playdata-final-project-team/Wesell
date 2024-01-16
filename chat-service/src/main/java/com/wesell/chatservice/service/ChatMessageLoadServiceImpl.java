package com.wesell.chatservice.service;

import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.repository.ChatMessageRepository;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatMessageLoadService;
import com.wesell.chatservice.dto.query.ChatMessageListQuery;
import com.wesell.chatservice.dto.response.ChatMessageResponseDto;
import com.wesell.chatservice.exception.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageLoadServiceImpl implements ChatMessageLoadService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ChatMessageResponseDto> getChatMessageList(ChatMessageListQuery query) {
        ChatRoom room = chatRoomRepository.findById(query.getRoomId()).orElseThrow(
                () -> new ChatRoomNotFoundException()
        );

        Slice<ChatMessage> chatMessageSlice = chatMessageRepository
                .findAllByChatRoom(room,
                        PageRequest.of(query.getPage(),
                                query.getSize(),
                                Sort.by("sendDate").descending()));

        return chatMessageSlice.getContent().stream().map(
                (entity) -> ChatMessageResponseDto.builder()
                        .id(entity.getId())
                        .message(entity.getContent())
                        .sender(entity.getSender())
                        .build()
        ).collect(Collectors.toList());
    }
}
