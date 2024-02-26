package com.wesell.boardservice.domain.dto.reponse;

import com.wesell.boardservice.domain.entity.Post;
import lombok.*;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AllPostsResponseDto implements Serializable {
    private Long id;
    private String title;
    private String writer;
    private String createdAt;
    private Long click;

    public AllPostsResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.createdAt = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.click = post.getClick();
    }
    public static List<AllPostsResponseDto> of(List<Post> postList) {
        return postList.stream()
                .map(post -> new AllPostsResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getWriter(),
                        post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        post.getClick()
                ))
                .collect(Collectors.toList());
    }
}
