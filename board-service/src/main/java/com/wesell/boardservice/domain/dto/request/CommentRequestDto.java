package com.wesell.boardservice.domain.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
    private String content;
    private Long postId;
    private String writer;
    private Long parentId;

}
