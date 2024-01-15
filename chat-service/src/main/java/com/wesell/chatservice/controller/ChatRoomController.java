package com.wesell.chatservice.controller;

import com.wesell.chatservice.domain.service.ChatRoomCreateService;
import com.wesell.chatservice.domain.service.ChatRoomLoadService;
import com.wesell.chatservice.dto.command.ChatRoomCreateCommand;
import com.wesell.chatservice.dto.query.ChatRoomListQuery;
import com.wesell.chatservice.dto.query.ChatRoomQuery;
import com.wesell.chatservice.dto.request.ChatRoomRequest;
import com.wesell.chatservice.dto.response.ChatRoomListResponse;
import com.wesell.chatservice.dto.response.ChatRoomResponse;
import com.wesell.chatservice.global.response.success.SuccessApiResponse;
import com.wesell.chatservice.global.response.success.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/rooms")
public class ChatRoomController {

    private final ChatRoomCreateService chatRoomCreateService;
    private final ChatRoomLoadService chatRoomLoadService;

    /**
     * 채팅방 생성
     *
     * @param requestDto
     * @return ResponseEntity<Long>
     */
    @PostMapping
    public ResponseEntity<?> createChatRoom(@RequestBody @Valid ChatRoomRequest requestDto) {
        ChatRoomCreateCommand chatRoomCreateCommand = ChatRoomCreateCommand.builder()
                .productId(requestDto.getProductId())
                .consumer(requestDto.getConsumer())
                .seller(requestDto.getSeller())
                .build();
        return ResponseEntity.ok(SuccessApiResponse.of(
                SuccessCode.ROOM_CREATED,
                chatRoomCreateService.createChatRoom(chatRoomCreateCommand))
        );
    }

    /**
     * 채팅방 내 메시지 조회 + 페이징(무한 스크롤 구현 예정)
     *
     * @param roomId
     * @param page
     * @param size
     * @return ResponseEntity<ChatRoomResponse>
     */
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getChatRoom(@PathVariable Long roomId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        ChatRoomQuery chatRoomQuery = ChatRoomQuery.builder()
                .id(roomId)
                .page(page)
                .size(size)
                .build();
        ChatRoomResponse chatRoomResponse = chatRoomLoadService.getChatRoomById(chatRoomQuery);
        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK, chatRoomResponse));
    }

    /**
     * 회원 자신이 들어가있는 채팅방 리스트 조회 + 페이징
     *
     * @param page
     * @param size
     * @param consumer
     * @return ResponseEntity<ChatRoomListResponse>
     */
    @GetMapping
    public ResponseEntity<?> getMyChatRoomList(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size,
                                               @RequestParam String consumer) {
        ChatRoomListQuery chatRoomListQuery = ChatRoomListQuery.builder()
                .consumer(consumer)
                .page(page)
                .size(size)
                .build();
        ChatRoomListResponse chatRoomListResponse = chatRoomLoadService.getChatRoomList(chatRoomListQuery);
        return ResponseEntity.ok(SuccessApiResponse.of(
                SuccessCode.OK,
                chatRoomListResponse)
        );
    }
}