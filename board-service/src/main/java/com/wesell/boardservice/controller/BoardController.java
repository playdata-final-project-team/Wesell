package com.wesell.boardservice.controller;

import com.wesell.boardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class BoardController {

    private final BoardService boardService;

    // 게시판별 모든 게시물 조회
    @GetMapping("board/{boardId}")
    public ResponseEntity<?> getAllPosts(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "10") int size,
                                         @PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardService.getAllPosts(page, size, boardId));
    }

}
