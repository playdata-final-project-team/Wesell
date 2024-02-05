package com.wesell.boardservice.service;

import com.wesell.boardservice.domain.dto.reponse.AllPostsResponseDto;
import com.wesell.boardservice.domain.dto.reponse.PageResponseDto;
import com.wesell.boardservice.domain.entity.Board;
import com.wesell.boardservice.domain.entity.Post;
import com.wesell.boardservice.domain.repository.BoardRepository;
import com.wesell.boardservice.domain.repository.PostRepository;
import com.wesell.boardservice.error.ErrorCode;
import com.wesell.boardservice.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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

    // 모든 게시물 조회
    @Cacheable(key = "'page: ' + #page + ' boardId: ' + #boardId", value = "MAINPAGE_CACHE")
    public PageResponseDto getAllPosts(int page, Long boardId) {
        int pageLimit = 5;

        Page<Post> posts =postRepository.findAllByPostAndBoard(boardId, PageRequest.of(page, pageLimit)).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        return PageResponseDto.builder()
                .dtoList(posts.map(AllPostsResponseDto::new).toList())
                .page(page)
                .totalElements(posts.getTotalElements())
                .size(posts.getSize())
                .build();

    }

    public PageResponseDto getAllPostsNoRedis(int page, Long boardId) {
        int pageLimit = 5;

        Page<Post> posts =postRepository.findAllByPostAndBoard(boardId, PageRequest.of(page, pageLimit)).orElseThrow(
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
