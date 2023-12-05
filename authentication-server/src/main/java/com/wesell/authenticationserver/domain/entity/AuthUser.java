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
    @Column(name="a_uuid", nullable = false, length = 50)
    private String uuid;

    @Column(name = "a_email", nullable = false, length = 60)
    private String email;

    @Column(name= "a_password", length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name= "a_role", nullable = false)
    private Role role;

    private boolean isDelete; // 삭제 여부

    private boolean isForced; // 강제 탈퇴 여부

    public void changeRole(Role role){
        this.role = role;
    }

    public void changeIsForced(){
        isForced = !isForced;
    }

    public void changeIsDelted(){
        isDelete = !isDelete;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
