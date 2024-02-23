package com.wesell.boardservice.controller;

import com.wesell.boardservice.domain.dto.reponse.CommentResponseDto;
import com.wesell.boardservice.domain.dto.reponse.PageResponseDto;
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
    public ResponseEntity<?> findAllCommentsByPostId(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                         @RequestParam(name = "size", defaultValue = "10") int size,
                                                                         @PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.findCommentsWithPage(postId, page, size));
    }
    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.createComment(commentRequestDto));
    }
    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }
}
