package com.wesell.chatservice.service;

import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.repository.ChatMessageRepository;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatMessageService;
import com.wesell.chatservice.dto.request.ChatMessageRequestDto;
import com.wesell.chatservice.dto.response.ChatMessageListResponseDto;
import com.wesell.chatservice.dto.response.ChatMessageResponseDto;
import com.wesell.chatservice.global.response.error.CustomException;
import com.wesell.chatservice.global.response.error.ErrorCode;
import com.wesell.chatservice.global.util.DateFormatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final DateFormatUtil dateFormatUtil;

    @Transactional
    @Override
    public ChatMessageResponseDto createChatMessage(ChatMessageRequestDto requestDto) {

        log.debug("채팅 메시지 생성 시작");

        log.debug("채팅 방 번호로 조회 - 없으면 예외처리");
        ChatRoom chatRoom = chatRoomRepository.findById(requestDto.getRoomId()).orElseThrow(
                () -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND)
        );

        log.debug("채팅 메시지 생성");
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(requestDto.getSender())
                .content(requestDto.getMessage())
                .sendDate(LocalDateTime.now())
                .build();

        log.debug("Cascade 를 이용한 데이터 생성 및 저장");
        chatRoom.createMessage(chatMessage);
        chatRoomRepository.save(chatRoom); // DB에 메시지 저장

        return ChatMessageResponseDto.builder()
                .roomId(chatMessage.getChatRoom().getId())
                .sender(chatMessage.getSender())
                .content(chatMessage.getContent())
                .sendDate(dateFormatUtil.formatSendDate(chatMessage.getSendDate()))
                .build();
    }

    @Override
    public ChatMessageListResponseDto getChatMessageList(String roomId, int page, int size) {
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND)
        );

        // todo : 개선 필요!
        Slice<ChatMessage> chatMessageSlice =
                chatMessageRepository.findAllByChatRoom(room, PageRequest.of(page,size,
                        Sort.by("sendDate").descending()));

        List<ChatMessageResponseDto> messages = chatMessageSlice.getContent().stream().map(
                (entity) -> ChatMessageResponseDto.builder()
                        .roomId(entity.getChatRoom().getId())
                        .content(entity.getContent())
                        .sender(entity.getSender())
                        .sendDate(dateFormatUtil.formatSendDate(entity.getSendDate()))
                        .build()
        ).collect(Collectors.toList());

        return ChatMessageListResponseDto.builder()
                .messages(messages)
                .build();
    }
}
