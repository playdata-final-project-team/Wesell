package com.wesell.boardservice.controller;

import com.wesell.boardservice.domain.dto.reponse.PageResponseDto;
import com.wesell.boardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("api/v1/redis")
public class RedisTestController {

    private final BoardService boardService;

    @GetMapping("{boardId}/1")
    public ResponseEntity<?> selectNoRedis(@RequestParam(name = "page", defaultValue = "1")Integer page, @PathVariable("boardId")Long boardId) {

        long startTime = System.currentTimeMillis();
        PageResponseDto result = boardService.getAllPostsNoRedis(page, boardId);
        log.info("캐시 적용 전 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 전 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 전 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 전 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 전 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 전 :" + (System.currentTimeMillis() - startTime)+ "ms");
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("{boardId}/2")
    public ResponseEntity<?> selectRedis(@RequestParam(name = "page", defaultValue = "1")Integer page,
                                         @RequestParam(name = "size", defaultValue = "8") int size,
                                         @PathVariable("boardId")Long boardId) {

        long startTime = System.currentTimeMillis();
        PageResponseDto result = boardService.getAllPosts(page,size, boardId);
        log.info("캐시 적용 후 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 후 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 후 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 후 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 후 :" + (System.currentTimeMillis() - startTime)+ "ms");
        log.info("캐시 적용 후 :" + (System.currentTimeMillis() - startTime)+ "ms");
        return ResponseEntity.ok().body(result);
    }
}
