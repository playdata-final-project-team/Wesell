package com.wesell.dealservice.controller;

import com.wesell.dealservice.domain.dto.request.RequestCreateDealPostDto;
import com.wesell.dealservice.service.DealServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("deal")
public class DealController {

    private final DealServiceImpl dealService;

    @PostMapping("post")
    public ResponseEntity<?> createDealPost(@Valid @RequestBody RequestCreateDealPostDto registerDto) {
        dealService.createDealPost(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
