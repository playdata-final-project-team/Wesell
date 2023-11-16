package com.wesell.authenticationserver.domain.entity;

import com.wesell.authenticationserver.domain.enum_.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="auth")
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private Long id;

    @Column(name = "a_email", nullable = false, unique = true, length = 60)
    private String email;

    @Column(name= "a_password", length = 100)
    private String password;

    @Column(name="a_uuid", nullable = false, length = 50)
    private String uuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean isDelete; // 삭제 여부

    private boolean isForced; // 강제 탈퇴 여부

    public void changeRole(Role role){
        this.role = role;
    }
}
