package com.wesell.authenticationserver.domain.entity;

import com.wesell.authenticationserver.domain.enum_.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="auth")
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private Long id;

    @Column(name = "a_email", nullable = false, unique = true, length = 60)
    private String email;

    @Column(name= "a_password", length = 30)
    private String password;

    @Column(name="a_uuid", nullable = false, length = 20)
    private String uuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault(value = "USER")
    private Role role;
}
