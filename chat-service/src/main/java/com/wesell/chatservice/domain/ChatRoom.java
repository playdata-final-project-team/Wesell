package com.wesell.chatservice.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cr_id")
    private Long id;

    @Column(name = "p_id")
    private Long productId;

    @Column(name = "cr_consumer_uuid")
    private String consumer;

    @Builder
    public ChatRoom(String consumer, Long productId) {
        this.consumer = consumer;
        this.productId = productId;
    }

    public static ChatRoom createRoom(String consumer, Long productId) {
        return ChatRoom.builder()
                .consumer(consumer)
                .productId(productId)
                .build();
    }

}
