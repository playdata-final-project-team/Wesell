package com.wesell.boardservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "p_id")
    private Long id;

    @CreationTimestamp
    @Column(name = "p_createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "p_writer", nullable = false)
    private String writer;

    @Column(name = "p_title", nullable = false)
    private String title;

    @ColumnDefault("0")
    @Column(name = "p_click", nullable = false)
    private Long click;

    @Column(name = "p_content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "b_id")
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "parent_id is null")
    private List<Comment> commentList = new ArrayList<>();

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void addClick() {
        this.click++;
    }
}
