package com.wesell.chatservice.service;

import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.feign.ImageFeignService;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatRoomLoadService;
import com.wesell.chatservice.dto.query.ChatRoomListQuery;
import com.wesell.chatservice.dto.response.ChatRoomListResponseDto;
import com.wesell.chatservice.dto.response.ChatRoomResponseDto;
import com.wesell.chatservice.global.response.error.CustomException;
import com.wesell.chatservice.global.response.error.ErrorCode;
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

    @Override
    public ChatRoomListResponseDto getChatRoomList(ChatRoomListQuery query) {
        Slice<ChatRoom> chatRoomSlice = chatRoomRepository.findAllByConsumer(query.getConsumer(),
                PageRequest.of(query.getPage(), query.getSize(), Sort.by("id").descending()));

        Map<Long, String> urls = initImageUrl(chatRoomSlice);

        return ChatRoomListResponseDto.builder()
                .roomList(chatRoomSlice.getContent().stream().map((entity) -> ChatRoomResponseDto.builder()
                        .roomId(entity.getId())
                        .seller(entity.getSeller())
                        .imageUrl(urls.get(entity.getProductId()))
                        .lastSendDate(searchUtil.searchLastSendDate(entity))
                        .lastSendMessage(searchUtil.searchLastSendMessage(entity))
                        .build()).collect(Collectors.toList()))
                .hasNext(chatRoomSlice.hasNext()).build();
    }

    // imageUrl 을 open feign 으로 가져오는 로직
    private Map<Long, String> initImageUrl(Slice<ChatRoom> chatRoomSlice) {
        try {
            return imageFeignService.getUrlByProductId(chatRoomSlice.stream()
                    .map(ChatRoom::getProductId)
                    .map(Long::parseLong)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_SERVER_FEIGN_ERROR);
        }
    }


}
