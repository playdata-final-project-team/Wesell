package com.wesell.boardservice.controller;

import com.wesell.boardservice.domain.dto.request.PostRequestDto;
import com.wesell.boardservice.domain.dto.request.PostUpdateRequestDto;
import com.wesell.boardservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PostController {

    private final PostService postService;

    // 게시글 등록기능
    @PostMapping("/post/{boardId}")
    public ResponseEntity<?> post(@RequestBody PostRequestDto postRequestDto, @PathVariable("boardId") Long boardId) {
        postService.save(postRequestDto, boardId);
        return ResponseEntity.ok("게시물이 등록되었습니다.");
    }

    //게시글 삭제기능
    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<?> deletePostInfo(@PathVariable("postId")Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시물이 삭제되었습니다.");
    }

    //게시물 수정기능
    @PutMapping("/post/update/{postId}")
    public ResponseEntity<?> updatePostInfo(@RequestBody PostUpdateRequestDto postUpdateRequestDto, @PathVariable("postId")Long postId) {
        postService.updatePost(postUpdateRequestDto, postId);
        return ResponseEntity.ok("게시물이 수정되었습니다.");
    }
    // 게시글 조회 기능
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostInfo(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }
}