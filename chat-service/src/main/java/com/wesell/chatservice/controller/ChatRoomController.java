package com.wesell.chatservice.controller;

import com.wesell.chatservice.domain.service.ChatRoomService;
import com.wesell.chatservice.dto.request.ChatRoomListRequestDto;
import com.wesell.chatservice.dto.request.ChatRoomRequestDto;
import com.wesell.chatservice.dto.response.ChatRoomListResponseDto;
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

    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 생성
     *
     * @param requestDto
     * @return ResponseEntity<Long>
     */
    @PostMapping
    public ResponseEntity<SuccessApiResponse<?>> createChatRoom(@RequestBody @Valid ChatRoomRequestDto requestDto) {
        String roomId = chatRoomService.createChatRoom(requestDto);

        return ResponseEntity.ok(SuccessApiResponse.of(
                SuccessCode.ROOM_CREATED,
                roomId)
        );
    }

    /**
     * 채팅방 오픈
     * 
     * @param roomId
     * @return
     */
    @GetMapping("/{room-id}")
    public ResponseEntity<SuccessApiResponse<?>> openChatRoom(@PathVariable("room-id") String roomId){
        return ResponseEntity.ok(SuccessApiResponse.of(
                SuccessCode.OK,
                chatRoomService.findChatRoom(roomId)));
    }

    /**
     * 회원 자신이 들어가있는 채팅방 리스트 조회 + 페이징
     *
     * @param page
     * @param size
     * @param consumer
     * @return ResponseEntity<ChatRoomListResponseDto>
     */
    @GetMapping
    public ResponseEntity<SuccessApiResponse<?>> getMyChatRoomList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam String consumer) {
        ChatRoomListResponseDto chatRoomListResponseDto = chatRoomService.myChatRoomList(consumer, page, size);
        return ResponseEntity.ok(SuccessApiResponse.of(
                SuccessCode.OK,
                chatRoomListResponseDto)
        );
    }

    /**
     * 채팅방 나가기
     *
     * @param roomId
     * @param demander
     * @return
     */
    @DeleteMapping
    public ResponseEntity<SuccessApiResponse<?>> exitChatRoom(@RequestParam String roomId,
                                                              @RequestParam String demander)
    {
        chatRoomService.exitChatRoom(roomId,demander);

        return ResponseEntity.ok(SuccessApiResponse.of(SuccessCode.OK));
    }
}
