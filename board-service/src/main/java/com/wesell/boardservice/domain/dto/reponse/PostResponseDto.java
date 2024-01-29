package com.wesell.boardservice.domain.dto.reponse;

import com.wesell.boardservice.domain.entity.Comment;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {
    private String title;
    private String content;
    private Long click;
    private List<Comment> comments;
}
