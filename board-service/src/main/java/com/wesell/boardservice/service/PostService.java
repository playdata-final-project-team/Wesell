package com.wesell.boardservice.service;

import com.wesell.boardservice.domain.dto.reponse.PostResponseDto;
import com.wesell.boardservice.domain.dto.request.PostRequestDto;
import com.wesell.boardservice.domain.entity.Board;
import com.wesell.boardservice.domain.entity.Comment;
import com.wesell.boardservice.domain.entity.Post;
import com.wesell.boardservice.domain.repository.BoardRepository;
import com.wesell.boardservice.domain.repository.CommentRepository;
import com.wesell.boardservice.domain.repository.PostRepository;
import com.wesell.boardservice.error.ErrorCode;
import com.wesell.boardservice.error.exception.CustomException;
import com.wesell.boardservice.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserFeignClient userFeignClient;

    // 글 작성 서비스 로직
    public void save(PostRequestDto postRequestDto, Long boardId) {

        String writer = userFeignClient.findNicknameByUuid(postRequestDto.getUuid());

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .click(0L)
                .writer(writer)
                .board(board)
                .build();
        postRepository.save(post);
    }

    // 게시물 상세 조회
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
        List<Comment> comments = commentRepository.findCommentByPostId(post.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        post.addClick();
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .comments(comments)
                .click(post.getClick())
                .build();
    }
}
