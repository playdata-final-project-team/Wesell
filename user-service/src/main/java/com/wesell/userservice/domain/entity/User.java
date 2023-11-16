package com.wesell.userservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import java.time.LocalDateTime;

@Entity
@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_name", nullable = false)
    private String name;

    @Column(name = "u_nickname", length = 15, nullable = false)
    private String nickname;

    @Column(name = "u_phone", nullable = false)
    private String phone;

    @CreationTimestamp
    @Column(name = "u_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @ColumnDefault(value = "false")
    @Column(name = "u_agree", nullable = false)
    private boolean agree;

    @Column(name = "u_uuid", nullable = false, unique = true)
    private String uuid;

    public User changeName(String name){
        this.name = name;
        return this;
    }

}
