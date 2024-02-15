package com.wesell.boardservice.domain.dto.reponse;

import com.wesell.boardservice.domain.entity.Comment;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto implements Serializable {
    private String boardTitle;
    private String title;
    private String writer;
    private String createdAt;
    private String content;
    private Long click;
    private List<Comment> comments;
}
