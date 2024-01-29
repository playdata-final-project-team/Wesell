package com.wesell.authenticationserver.domain.entity;

import com.wesell.authenticationserver.domain.enum_.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="kakao")
public class KakaoUser {
    @Id
    @Column(name="k_uuid", nullable = false, length = 50)
    private String uuid;

    @Column(name = "k_email", unique = true, nullable = false, length = 60)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name= "k_role", nullable = false, length = 20)
    private Role role;

    @Column(name = "is_deleted")
    private boolean isDeleted; // 삭제 여부

    @Column(name = "is_forced")
    private boolean isForced; // 강제 탈퇴 여부

    public void changeRole(Role role){
        this.role = role;
    }

    public void changeIsForced(){
        isForced = !isForced;
    }

    public void changeIsDeleted(){
        isDeleted = !isDeleted;
    }
}
