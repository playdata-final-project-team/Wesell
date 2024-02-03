package com.wesell.boardservice.domain.dto.reponse;

import com.wesell.boardservice.domain.entity.Comment;
import com.wesell.boardservice.domain.entity.DeleteStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDto {
    private Long id;
    private String content;
    private String writer;
    private List<CommentResponseDto> children = new ArrayList<>();
    private String createdAt;

    public CommentResponseDto(Long id, String content, String writer, String createdAt) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
    }

    public static CommentResponseDto convertCommentToDto(Comment comment) {
        return comment.getIsDeleted() == DeleteStatus.Y ?
                new CommentResponseDto(comment.getId(), "삭제된 댓글입니다.", null, comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))) :
                new CommentResponseDto(comment.getId(), comment.getContent(), comment.getWriter(), comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
