package com.wesell.dealservice.controller;

import com.wesell.dealservice.dto.request.CreateDealPostRequestDto;
import com.wesell.dealservice.dto.request.EditPostRequestDto;
import com.wesell.dealservice.service.CategoryServiceImpl;
import com.wesell.dealservice.service.DealServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("deal-service")
public class DealController {

    private final DealServiceImpl dealService;
    private final CategoryServiceImpl categoryService;

    @PostMapping("post")
    public ResponseEntity<?> createDealPost(@Valid @RequestBody CreateDealPostRequestDto registerDto) {
        dealService.createDealPost(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("post")
    public ResponseEntity<?> getPostInfo(@Valid @RequestParam("id") Long postId) {
        return new ResponseEntity<>(dealService.getPostInfo(postId), HttpStatus.OK);
    }

    @PutMapping("edit")
    public ResponseEntity<?> editPost(@Valid @RequestBody EditPostRequestDto requestDto, @RequestParam("id") Long postId) {
        return new ResponseEntity<>(dealService.editPost(requestDto, postId),HttpStatus.OK);
    }

    @PutMapping("complete")
    public ResponseEntity<?> changePostStatus(@Valid @RequestParam("id") Long postId) {
        dealService.changePostStatus(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deletePost(@Valid @RequestParam("id") Long postId) {
        dealService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<?> getMyPostInfo(@RequestParam("uuid") String uuid) {
        return new ResponseEntity<>(dealService.getMyPostList(uuid),HttpStatus.OK);
    }

    @GetMapping("main")
    public ResponseEntity<?> getMainInfo() {
        Map<String, Object> mainInfo = new HashMap<>();
        mainInfo.put("categoryInfo", categoryService.getMainPageInfo());
        mainInfo.put("dealInfo", dealService.getMainPageInfo());

        return new ResponseEntity<>(mainInfo, HttpStatus.OK);
    }

}
