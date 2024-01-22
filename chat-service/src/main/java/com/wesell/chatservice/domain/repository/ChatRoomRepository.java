package com.wesell.chatservice.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wesell.chatservice.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {

    // 만들어진 채팅방 유무 확인
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.productId = :productId AND cr.consumer = :consumer")
    List<ChatRoom> findALlByProductIdAndConsumer(@Param("productId") Long productId, @Param("consumer") String consumer);

    // 구매자 자신이 열어놓은 채팅방 목록
    @Query("SELECT c FROM ChatRoom c WHERE c.consumer = :consumer")
    Slice<ChatRoom> findAllByConsumer(@Param("consumer") String consumer, Pageable pageable);

    boolean existsChatRoomById(String roomId);
}
