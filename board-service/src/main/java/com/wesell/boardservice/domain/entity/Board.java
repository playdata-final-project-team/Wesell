package com.wesell.boardservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "b_id")
    private Long id;

    @Column(name = "b_title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();



}
