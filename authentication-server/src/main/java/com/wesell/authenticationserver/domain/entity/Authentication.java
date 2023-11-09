package com.wesell.authenticationserver.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
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

    @Column(name="uuid", nullable = false, length = 20)
    private String uuid;
}
