package com.wesell.userservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@DynamicInsert
public class User {

    @Id
    @GeneratedValue
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_name", nullable = false)
    private String name;

    @Column(name = "u_nickname", length = 15, nullable = false)
    private String nickname;

    @Column(name = "u_phone", length = 11, nullable = false)
    private String phone;

    @CreationTimestamp
    @Column(name = "u_createdAt", nullable = false)
    private Date createdAt;

    @ColumnDefault(value = "false")
    @Column(name = "u_isforced", nullable = false)
    private boolean isforced;

    @ColumnDefault(value = "false")
    @Column(name = "u_agree", nullable = false)
    private boolean agree;

    @Column(name = "u_uuid", nullable = false)
    private String uuid;


}
