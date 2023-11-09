package com.wesell.userservice.domain.entity;

import com.wesell.userservice.domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.Length;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "u_role", nullable = false)
    private Role role;

    @Temporal(TemporalType.DATE)
    @Column(name = "u_createdAt", nullable = false)
    private Date createdAt;

    @ColumnDefault(value = "false")
    @Column(name = "u_isforced", nullable = false)
    private boolean isforced;

    @ColumnDefault(value = "false")
    @Column(name = "u_agree", nullable = false)
    private boolean agree;

    @Column(name = "uuid", nullable = false)
    private String uid;


}
