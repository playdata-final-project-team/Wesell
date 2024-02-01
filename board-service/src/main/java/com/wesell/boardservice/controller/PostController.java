package com.wesell.boardservice.controller;

import com.wesell.boardservice.domain.dto.request.PostRequestDto;
import com.wesell.boardservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PostController {

    private final PostService postService;

    // 글 등록 폼으로 이동
    @GetMapping("/register")
    public ResponseEntity<?> register() {
        return ResponseEntity.ok(null);
    }
    // 게시글 등록기능
    @PostMapping("/post/{boardId}")
    public ResponseEntity<?> post(@RequestBody PostRequestDto postRequestDto, @PathVariable("boardId") Long boardId) {
        postService.save(postRequestDto, boardId);
        return ResponseEntity.ok("게시물이 등록되었습니다.");
    }
    // 게시글 조회 기능
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostInfo(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }
}