package com.wesell.chatservice.domain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.domain.entity.ChatRoom;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    Slice<ChatMessage> findAllByChatRoom(ChatRoom chatRoom, PageRequest pageRequest);
}
