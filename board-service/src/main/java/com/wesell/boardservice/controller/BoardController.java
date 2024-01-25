package com.wesell.boardservice.controller;

import com.wesell.boardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("board")
    public ResponseEntity<?> getAllPosts(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(boardService.getAllPosts(page-1));
    }
}
