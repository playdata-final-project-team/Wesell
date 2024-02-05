package com.wesell.boardservice.service;

import com.wesell.boardservice.domain.dto.reponse.CommentResponseDto;
import com.wesell.boardservice.domain.dto.request.CommentRequestDto;
import com.wesell.boardservice.domain.entity.Comment;
import com.wesell.boardservice.domain.entity.DeleteStatus;
import com.wesell.boardservice.domain.entity.Post;
import com.wesell.boardservice.domain.repository.CommentRepository;
import com.wesell.boardservice.domain.repository.CommentRepositoryImpl;
import com.wesell.boardservice.domain.repository.PostRepository;
import com.wesell.boardservice.error.ErrorCode;
import com.wesell.boardservice.error.exception.CustomException;
import com.wesell.boardservice.feignClient.UserFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    // 댓글 조회
    public List<CommentResponseDto> findCommentsByPostId(Long postId) {
        postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        List<Comment> comments = commentRepository.findCommentByPostId(postId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        return convertNestedStructure(comments);
    }
    // 댓글 저장
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.save(
                Comment.createComment(commentRequestDto.getContent(),
                        postRepository.findById(commentRequestDto.getPostId()).orElseThrow(
                                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
                        ),
                        commentRequestDto.getWriter(),
                        commentRequestDto.getParentId() != null ?
                            commentRepository.findById(commentRequestDto.getParentId()).orElseThrow(
                                    () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
                            ) : null, LocalDateTime.now())
        );
                        return CommentResponseDto.convertCommentToDto(comment);

    }
    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findCommentByIdWithParent(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
        if(comment.getChildren().size() != 0) {
            comment.changeDeletedStatus(DeleteStatus.Y);
        } else {
            commentRepository.delete(getDeleteAncestorComment(comment));
        }
    }
    // 부모 댓글 삭제 로직
    private Comment getDeleteAncestorComment(Comment comment) {
        Comment parent = comment.getParent();
        if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted() == DeleteStatus.Y)
            return getDeleteAncestorComment(parent);
        return comment;
    }
    // 대댓글 중첩 구조로 변환하는 로직
    private List<CommentResponseDto> convertNestedStructure(List<Comment> comments) {
        List<CommentResponseDto> result = new ArrayList<>();
        Map<Long, CommentResponseDto> map = new HashMap<>();
        comments.stream().forEach(c -> {
            CommentResponseDto dto = CommentResponseDto.convertCommentToDto(c);
            map.put(dto.getId(), dto);
            if(c.getParent() != null)
                map.get(c.getParent().getId()).getChildren().add(dto);
            else
                result.add(dto);
        });
        return result;
    }
}
