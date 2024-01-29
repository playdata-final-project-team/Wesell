package com.wesell.boardservice.service;

import com.wesell.boardservice.domain.dto.reponse.AllPostsResponseDto;
import com.wesell.boardservice.domain.dto.reponse.PageResponseDto;
import com.wesell.boardservice.domain.entity.Post;
import com.wesell.boardservice.domain.repository.BoardRepository;
import com.wesell.boardservice.domain.repository.PostRepository;
import com.wesell.boardservice.error.ErrorCode;
import com.wesell.boardservice.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    public PageResponseDto getAllPosts(int page) {
        int pageLimit = 5;
        Page<Post> posts =postRepository.findAllByPost(PageRequest.of(page, pageLimit)).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        return PageResponseDto.builder()
                .dtoList(posts.map(AllPostsResponseDto::new).toList())
                .page(page)
                .totalElements(posts.getTotalElements())
                .size(posts.getSize())
                .build();

    }
}
