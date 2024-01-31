package com.wesell.boardservice.domain.dto.reponse;

import com.wesell.boardservice.domain.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AllPostsResponseDto {
    private String title;
    private String writer;
    private LocalDateTime createdAt;

    public AllPostsResponseDto(Post post) {
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.createdAt = LocalDateTime.now();
    }
    public static List<AllPostsResponseDto> of(List<Post> postList) {
        return postList.stream()
                .map(post -> new AllPostsResponseDto(
                        post.getTitle(),
                        post.getWriter(),
                        LocalDateTime.now()
                ))
                .collect(Collectors.toList());
    }
}
