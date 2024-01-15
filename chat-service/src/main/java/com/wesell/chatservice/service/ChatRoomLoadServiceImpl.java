package com.wesell.chatservice.service;

import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatRoomLoadService;
import com.wesell.chatservice.dto.query.ChatRoomListQuery;
import com.wesell.chatservice.dto.query.ChatRoomQuery;
import com.wesell.chatservice.dto.response.ChatMessageResponse;
import com.wesell.chatservice.dto.response.ChatRoomListResponse;
import com.wesell.chatservice.dto.response.ChatRoomResponse;
import com.wesell.chatservice.exception.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomLoadServiceImpl implements ChatRoomLoadService {
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoomResponse getChatRoomById(ChatRoomQuery query) {
        ChatRoom room = chatRoomRepository.findById(query.getId()).orElseThrow(
                () -> new ChatRoomNotFoundException()
        );

//        if(room.getChatMessageList().isEmpty()){
//            return ChatRoomResponse.builder()
//                    .roomId(room.getId())
//                    .build();
//        }
//
//        List<ChatMessageResponse> chatMessageList = room.getChatMessageList().stream().map((entity) ->
//                ChatMessageResponse.builder()
//                        .id(entity.getId())
//                        .message(entity.getContent())
//                        .sender(entity.getSender())
//                        .sendDate(entity.getSendDate().toString())
//                        .build()
//        ).collect(Collectors.toList());

        return ChatRoomResponse.builder()
                .roomId(room.getId())
                .build();
    }

    @Override
    public ChatRoomListResponse getChatRoomList(ChatRoomListQuery query) {
        Slice<ChatRoom> chatRoomSlice = chatRoomRepository
                .findAllByConsumer(PageRequest.of(query.getPage(), query.getSize()), query.getConsumer());
        return ChatRoomListResponse.builder()
                .roomList(chatRoomSlice.getContent().stream().map(
                        (entity) -> ChatRoomResponse.builder()
                                .roomId(entity.getId())
                                .seller(entity.getSeller())
                                .build()
                ).collect(Collectors.toList()))
                .hasNext(chatRoomSlice.hasNext())
                .build();
    }
}
