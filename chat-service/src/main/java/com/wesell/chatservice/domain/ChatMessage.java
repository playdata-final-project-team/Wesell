package com.wesell.chatservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "cr_id")
    private ChatRoom room;

    @Column(name = "cm_sender")
    private String sender; // nickname

    @Column(columnDefinition = "TEXT", name = "cm_message")
    private String content;

    @Column(name = "cm_sender_date", updatable = false)
    @CreatedDate
    private LocalDateTime sendDate;

    @Builder
    public ChatMessage(ChatRoom room, String sender, String content) {
        this.room = room;
        this.sender = sender;
        this.content = content;
        this.sendDate = LocalDateTime.now();
    }

    /**
     * 채팅 생성
     *
     * @param room           채팅 방
     * @param sender         보낸이(nickname)
     * @param content        내용
     * @return ChatMessage 엔티티
     */
    public static ChatMessage createMessage(ChatRoom room, String sender, String content) {
        return ChatMessage.builder()
                .room(room)
                .sender(sender)
                .content(content)
                .build();
    }

}
