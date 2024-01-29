package com.wesell.chatservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "chat_room")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cr_id")
    private String id;

    @Column(name = "p_id", nullable = false)
    private String productId;

    @Column(name = "cr_consumer_uuid", length = 50, nullable = false)
    private String consumer;

    @Column(name = "cr_seller_nickname", length = 50, nullable = false)
    private String seller;

    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<ChatMessage> chatMessageList = new ArrayList<>();

    public void createMessage(ChatMessage message){
        chatMessageList.add(message);
    }

}
