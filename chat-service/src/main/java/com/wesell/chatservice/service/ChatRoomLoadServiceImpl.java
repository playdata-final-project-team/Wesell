package com.wesell.chatservice.service;

import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatRoomLoadService;
import com.wesell.chatservice.dto.query.ChatRoomListQuery;
import com.wesell.chatservice.dto.query.ChatRoomQuery;
import com.wesell.chatservice.dto.response.ChatRoomListResponseDto;
import com.wesell.chatservice.dto.response.ChatRoomResponseDto;
import com.wesell.chatservice.exception.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomLoadServiceImpl implements ChatRoomLoadService {
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoomResponseDto getChatRoomById(ChatRoomQuery query) {
        ChatRoom room = chatRoomRepository.findById(query.getId()).orElseThrow(
                () -> new ChatRoomNotFoundException()
        );

//        if(room.getChatMessageList().isEmpty()){
//            return ChatRoomResponseDto.builder()
//                    .roomId(room.getId())
//                    .build();
//        }
//
//        List<ChatMessageResponseDto> chatMessageList = room.getChatMessageList().stream().map((entity) ->
//                ChatMessageResponseDto.builder()
//                        .id(entity.getId())
//                        .message(entity.getContent())
//                        .sender(entity.getSender())
//                        .sendDate(entity.getSendDate().toString())
//                        .build()
//        ).collect(Collectors.toList());

        return ChatRoomResponseDto.builder()
                .roomId(room.getId())
                .build();
    }

    @Override
    public ChatRoomListResponseDto getChatRoomList(ChatRoomListQuery query) {
        Slice<ChatRoom> chatRoomSlice = chatRoomRepository
                .findAllByConsumer(PageRequest.of(query.getPage(), query.getSize()), query.getConsumer());
        return ChatRoomListResponseDto.builder()
                .roomList(chatRoomSlice.getContent().stream().map(
                        (entity) -> ChatRoomResponseDto.builder()
                                .roomId(entity.getId())
                                .seller(entity.getSeller())
                                .build()
                ).collect(Collectors.toList()))
                .hasNext(chatRoomSlice.hasNext())
                .build();
    }
}
