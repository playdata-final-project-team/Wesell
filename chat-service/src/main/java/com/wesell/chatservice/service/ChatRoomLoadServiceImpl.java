package com.wesell.chatservice.service;

import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.feign.ImageFeignService;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatRoomLoadService;
import com.wesell.chatservice.dto.query.ChatRoomListQuery;
import com.wesell.chatservice.dto.response.ChatRoomListResponseDto;
import com.wesell.chatservice.dto.response.ChatRoomResponseDto;
import com.wesell.chatservice.global.util.SearchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomLoadServiceImpl implements ChatRoomLoadService {
    private final ChatRoomRepository chatRoomRepository;
    private final ImageFeignService imageFeignService;
    private final SearchUtil searchUtil;
    private Map<Long,String> urls;

    @Override
    public ChatRoomListResponseDto getChatRoomList(ChatRoomListQuery query) {
        Slice<ChatRoom> chatRoomSlice = chatRoomRepository.findAllByConsumer(query.getConsumer(),
                PageRequest.of(query.getPage(), query.getSize(), Sort.by("id").descending()));

        initImageUrl(chatRoomSlice);

        return ChatRoomListResponseDto.builder()
                .roomList(chatRoomSlice.getContent().stream().map((entity) -> ChatRoomResponseDto.builder()
                        .roomId(entity.getId())
                        .seller(entity.getSeller())
                        .imageUrl(urls.get(entity.getProductId()))
                        .lastSendDate(searchUtil.searchLastSendDate(entity))
                        .build()).collect(Collectors.toList()))
                .hasNext(chatRoomSlice.hasNext()).build();
    }

    private void initImageUrl(Slice<ChatRoom> chatRoomSlice){
        urls = imageFeignService.getUrlByProductId(chatRoomSlice.stream()
                .map(ChatRoom::getProductId)
                .map(Long::parseLong)
                .collect(Collectors.toList()));
    }


}
