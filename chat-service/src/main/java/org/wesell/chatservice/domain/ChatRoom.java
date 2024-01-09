package org.wesell.chatservice.domain;

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
    private String consUuid;

    @Builder
    public ChatRoom(String consUuid, Long productId) {
        this.consUuid = consUuid;
        this.productId = productId;
    }

    public static ChatRoom createRoom(String consNickname, Long productId) {
        return ChatRoom.builder()
                .consUuid(consNickname)
                .productId(productId)
                .build();
    }

}
