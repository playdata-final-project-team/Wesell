package com.wesell.chatservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="chat_message")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cm_id")
    private Long id; // 메시지 id

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "cr_id", nullable = false)
    private ChatRoom chatRoom; // 채팅룸

    @Column(name = "cm_sender", nullable = false)
    private String sender; // 메시지 작성자 닉네임

    @Column(columnDefinition = "TEXT", name = "cm_message", nullable = false)
    private String content; // 메시지 내용

    @Column(name = "cm_sender_date", updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime sendDate; // 메시지 작성일

}
