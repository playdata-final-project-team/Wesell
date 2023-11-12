package com.wesell.userservice.domain.entity;

import com.wesell.userservice.domain.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@ToString
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
    @Column(name = "u_isforced", nullable = false)
    private boolean isforced;

    @ColumnDefault(value = "false")
    @Column(name = "u_agree", nullable = false)
    private boolean agree;

    @Column(name = "u_uuid", nullable = false)
    private String uuid;

    public static User toUserEntity(UserDTO userDTO){
        User userEntity = new User();
        userEntity.setId(userDTO.getId());
        userEntity.setName(userDTO.getName());
        userEntity.setNickname(userDTO.getNickname());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setUuid(userDTO.getUuid());
        userEntity.setCreatedAt(userDTO.getCreatedAt());

        return userEntity;
    }
}
