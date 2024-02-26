package com.wesell.boardservice.service;

import com.wesell.boardservice.domain.dto.reponse.CommentResponseDto;
import com.wesell.boardservice.domain.dto.reponse.PostResponseDto;
import com.wesell.boardservice.domain.dto.request.PostRequestDto;
import com.wesell.boardservice.domain.dto.request.PostUpdateRequestDto;
import com.wesell.boardservice.domain.entity.Board;
import com.wesell.boardservice.domain.entity.Comment;
import com.wesell.boardservice.domain.entity.Post;
import com.wesell.boardservice.domain.repository.BoardRepository;
import com.wesell.boardservice.domain.repository.CommentRepository;
import com.wesell.boardservice.domain.repository.PostRepository;
import com.wesell.boardservice.error.ErrorCode;
import com.wesell.boardservice.error.exception.CustomException;
import com.wesell.boardservice.feignClient.UserFeignClient;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserFeignClient userFeignClient;

    // 글 작성 서비스 로직
    public void save(PostRequestDto postRequestDto, Long boardId) {

    String writer;
    try {
        writer = userFeignClient.findNicknameByUuid(postRequestDto.getUuid());
    } catch (FeignException e) {
        throw new CustomException(ErrorCode.FEIGN_ERROR);
    }


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
    @Transactional
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        List<Comment> comments = commentRepository.findCommentByPostIdAndParentIsNull(post.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        List<CommentResponseDto> commentList = comments.stream().map(CommentResponseDto::convertCommentToDto
        ).toList();

        post.addClick();
        return PostResponseDto.builder()
                .boardTitle(post.getBoard().getTitle())
                .title(post.getTitle())
                .writer(post.getWriter())
                .content(post.getContent())
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(commentList)
                .click(post.getClick())
                .build();
    }

    // 게시글 삭제 로직
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
        postRepository.deleteById(post.getId());
    }

    // 게시글 수정 로직
    public void updatePost(PostUpdateRequestDto postUpdateRequestDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        post.updateTitle(postUpdateRequestDto.getTitle());
        post.updateContent(postUpdateRequestDto.getContent());
        postRepository.save(post);
    }
}
