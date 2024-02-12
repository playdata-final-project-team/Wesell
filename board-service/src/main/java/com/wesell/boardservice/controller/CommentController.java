package com.wesell.boardservice.controller;

import com.wesell.boardservice.domain.dto.reponse.CommentResponseDto;
import com.wesell.boardservice.domain.dto.request.CommentRequestDto;
import com.wesell.boardservice.domain.entity.Comment;
import com.wesell.boardservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CommentController {

    private final CommentService commentService;
    // 댓글 조회
    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentResponseDto>> findAllCommentsByPostId(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
    }
    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.createComment(commentRequestDto));
    }
    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }
}
