package com.wesell.chatservice.service;

import com.wesell.chatservice.domain.entity.ChatRoom;
import com.wesell.chatservice.domain.feign.ImageFeignService;
import com.wesell.chatservice.domain.repository.ChatRoomRepository;
import com.wesell.chatservice.domain.service.ChatRoomService;
import com.wesell.chatservice.dto.request.ChatRoomRequestDto;
import com.wesell.chatservice.dto.response.ChatMessageResponseDto;
import com.wesell.chatservice.dto.response.ChatRoomListResponseDto;
import com.wesell.chatservice.dto.response.ChatRoomResponseDto;
import com.wesell.chatservice.global.response.error.CustomException;
import com.wesell.chatservice.global.response.error.ErrorCode;
import com.wesell.chatservice.global.util.SearchUtil;
import com.wesell.chatservice.service.message_broker.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final RedisMessageListenerContainer messageListenerContainer;
    private final RedisSubscriber redisSubscriber;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ChatRoomRepository chatRoomRepository;
    private final ImageFeignService imageFeignService;
    private final SearchUtil searchUtil;

    private Map<String, ChannelTopic> topics; // 레디스 캐시에 추가 예정
    @PostConstruct
    public void init(){
        topics = new HashMap<>();
    }

    // 채팅방 생성
    @Override
    public String createChatRoom(ChatRoomRequestDto requestDto) {
        
        // 이미 만들어진 채팅방이 있는지 확인
        ChatRoom chatRoom = chatRoomRepository.findByProductIdAndConsumer(requestDto.getConsumer(), requestDto.getProductId())
                .orElse(ChatRoom.builder()
                        .id(UUID.randomUUID().toString())
                        .seller(requestDto.getSeller())
                        .consumer(requestDto.getConsumer())
                        .isAlive(true)
                        .productId(requestDto.getProductId()).build());

        String roomId = chatRoomRepository.save(chatRoom).getId();

        // Redis 메시지 브로커에 해당 roomId의 채널에 메시지 구현
        if (!topics.containsKey(roomId)) {
            ChannelTopic topic = new ChannelTopic("/sub/chat/"+roomId);
            messageListenerContainer.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }

        return roomId;
    }

    // 나의 채팅방 목록
    @Override
    public ChatRoomListResponseDto myChatRoomList(String consumer, int page, int size) {

        Slice<ChatRoom> chatRoomSlice = chatRoomRepository.findAllByConsumer(consumer,
                PageRequest.of(page, size, Sort.by("id").descending()));

        Map<Long, String> urls = initImageUrl(chatRoomSlice);

        return ChatRoomListResponseDto.builder()
                .roomList(chatRoomSlice.getContent().stream().map((entity) ->
                        ChatRoomResponseDto.builder()
                                .roomId(entity.getId())
                                .imageUrl(urls.get(entity.getProductId()))
                                .lastSendDate(searchUtil.searchLastSendDate(entity))
                                .lastSendMessage(searchUtil.searchLastSendMessage(entity))
                                .seller(entity.getSeller())
                                .isAlive(entity.isAlive())
                                .build()).collect(Collectors.toList()))
                .hasNext(chatRoomSlice.hasNext()).build();
    }

    // 채팅방 찾기
    @Override
    public ChatRoomResponseDto findChatRoom(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                ()->new CustomException(ErrorCode.CHATROOM_NOT_FOUND)
        );
        return ChatRoomResponseDto.builder()
                .roomId(chatRoom.getId())
                .seller(chatRoom.getSeller())
                .isAlive(chatRoom.isAlive())
                .consumer(chatRoom.getConsumer())
                .build();
    }

    // 채팅방 나가기
    @Override
    public void exitChatRoom(String roomId, String demander) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                ()->new CustomException(ErrorCode.CHATROOM_NOT_FOUND)      
        );
        
        // 나의 채팅방 목록에 노출되지 않도록 처리
        if(demander.equals(chatRoom.getSeller())){
            chatRoom.deleteChatRoomBySeller();
        }else if(demander.equals(chatRoom.getConsumer())){
            chatRoom.deleteChatRoomByConsumer();
        }
        chatRoom.deactivateChatRoom(); // 메시지 전송 못하도록 프론트에서 처리
        chatRoomRepository.save(chatRoom);

        ChannelTopic topic = topics.get(roomId);

        redisTemplate.convertAndSend(topic.getTopic(), ChatMessageResponseDto.builder().build());

        messageListenerContainer.removeMessageListener(redisSubscriber,topic);
    }

    @Override
    public String getTopic(String roomId) {
        return topics.get(roomId).getTopic();
    }

    // imageUrl 을 open feign 으로 가져오는 로직
    private Map<Long, String> initImageUrl(Slice<ChatRoom> chatRoomSlice) {
        try {
            return imageFeignService.getUrlByProductId(chatRoomSlice.stream()
                    .map(ChatRoom::getProductId)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.IMAGE_SERVER_FEIGN_ERROR);
        }
    }
}
