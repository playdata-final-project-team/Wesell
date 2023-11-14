package com.wesell.dealservice.controller;

import com.wesell.dealservice.domain.dto.request.CreateDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.service.DealServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("deal")
@CrossOrigin("http://127.0.0.1:5500")
public class DealController {

    private final DealServiceImpl dealService;

    @PostMapping("post")
    public ResponseEntity<?> createDealPost(@Valid @RequestBody CreateDealPostRequestDto registerDto) {
        dealService.createDealPost(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("edit")
    public ResponseEntity<?> editPost(@Valid @RequestBody EditPostRequestDto requestDto, @RequestParam("id") Long postId) {
        return new ResponseEntity<>(dealService.editPost(requestDto, postId),HttpStatus.OK);
    }

}
