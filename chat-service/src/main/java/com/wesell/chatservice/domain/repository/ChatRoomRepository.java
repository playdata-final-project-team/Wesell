package com.wesell.chatservice.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wesell.chatservice.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    // 구매자 자신이 열어놓은 채팅방 목록
    @Query("SELECT c FROM ChatRoom c WHERE c.consumer = :consumer")
    Slice<ChatRoom> findAllByConsumer(Pageable pageable, @Param("consumer") String consumer);

    boolean existsChatRoomById(Long id);
}
