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
    @Column(name = "cr_id", length = 100)
    private String id;

    @Column(name = "p_id", nullable = false)
    private Long productId;

    @Column(name = "cr_consumer_uuid", length = 50, nullable = false)
    private String consumer;

    @Column(name = "cr_seller_nickname", length = 50, nullable = false)
    private String seller;

    @Column(name ="cr_isAlive", nullable = false)
    private boolean isAlive;

    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<ChatMessage> chatMessageList = new ArrayList<>();

    public void createMessage(ChatMessage message){
        chatMessageList.add(message);
    }

    public void deactivateChatRoom(){
        this.isAlive = false;
    }

    public void deleteChatRoomByConsumer(){
        this.consumer = this.consumer + "-out";
    }

    public void deleteChatRoomBySeller(){
        this.seller = this.seller + "-out";
    }
}
