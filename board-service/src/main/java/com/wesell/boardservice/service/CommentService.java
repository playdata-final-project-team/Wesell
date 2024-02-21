package com.wesell.boardservice.service;

import com.wesell.boardservice.domain.dto.reponse.CommentResponseDto;
import com.wesell.boardservice.domain.dto.reponse.PageResponseDto;
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
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 조회 - 페이징 처리
    public PageResponseDto findCommentsWithPage(Long postId, int page, int size){

        List<Comment> allComments = commentRepository.findAll();

        Page<Comment> commentsPage = commentRepository.findAllByPostId(postId,
                PageRequest.of(page,size, Sort.by(Sort.Order.desc("createdAt"))));

        commentsPage.getContent().stream().forEach( c->
                log.info("------------------------------------------->>>>>111" + c)
        );

        List<CommentResponseDto> comments = convertNestedStructure(commentsPage.getContent(), allComments);

        comments.stream().forEach( c->
                log.info("------------------------------------------->>>>>222" + c)
        );

        return PageResponseDto.builder()
                .dtoList(comments)
                .page(page)
                .totalPages(commentsPage.getTotalPages())
                .size(commentsPage.getSize())
                .totalElements(commentsPage.getTotalElements())
                .build();
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

    // comment -> dto 변환
    public List<CommentResponseDto> convertNestedStructure(List<Comment> comments, List<Comment> allComments) {

        Map<Long, CommentResponseDto> map= new HashMap<>();
        
        // map 초기화(부모 댓글들 담기)
        comments.stream()
                .filter(comment -> Objects.isNull(comment.getParent()))
                .forEach(comment -> map.put(comment.getId(),CommentResponseDto.convertCommentToDto(comment)));

        // 댓글에 대댓글 담기
        allComments.stream()
                .filter(c -> Objects.nonNull(c.getParent()))
                .forEach(c -> {
                    CommentResponseDto parent = map.get(c.getParent().getId());
                    if (parent != null) {
                        parent.addChildren(CommentResponseDto.convertCommentToDto(c));
                    }
                });

        // map -> list 변환
        List<CommentResponseDto> list = new ArrayList<>(map.values());
        list.sort(Comparator.comparing(CommentResponseDto::getId));
        return list;
    }

    // 부모 댓글 삭제 로직
    private Comment getDeleteAncestorComment(Comment comment) {
        Comment parent = comment.getParent();
        if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted() == DeleteStatus.Y)
            return getDeleteAncestorComment(parent);
        return comment;
    }
}
