package com.wesell.boardservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "c_id")
    private Long id;

    @Column(name = "c_writer", nullable = false)
    private String writer;

    @Column(name = "c_content", nullable = false)
    @Lob
    private String content;

    @Column(name = "c_createdAt")
    private LocalDateTime createdAt;

    @Enumerated(value = EnumType.STRING)
    private DeleteStatus isDeleted;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public static Comment createComment(String content, Post post, String writer, Comment parent, LocalDateTime createdAt) {
        Comment comment = new Comment();
        comment.content = content;
        comment.post = post;
        comment.writer = writer;
        comment.parent = parent;
        comment.isDeleted = DeleteStatus.N;
        comment.createdAt = LocalDateTime.now();
        return comment;
    }

    public void changeDeletedStatus(DeleteStatus deleteStatus) {
        this.isDeleted = deleteStatus;
    }
}
