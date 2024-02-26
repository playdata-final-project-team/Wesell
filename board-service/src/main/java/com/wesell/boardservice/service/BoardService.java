package com.wesell.boardservice.service;

import com.wesell.boardservice.domain.dto.reponse.AllPostsResponseDto;
import com.wesell.boardservice.domain.dto.reponse.BoardListResponseDto;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    // 게시판 목록 조회
    public List<BoardListResponseDto> getAllBoards(){
        return boardRepository.findAll().stream().map(b ->
            BoardListResponseDto.builder()
                    .id(b.getId())
                    .title(b.getTitle())
                    .build()
        ).collect(Collectors.toList());
    }

    // 모든 게시물 조회
    @Cacheable(key = "'page: ' + #page + ' boardId: ' + #boardId", value = "MAINPAGE_CACHE")
    public PageResponseDto getAllPosts(int page, int size, Long boardId) {

        String title = boardRepository.findTitleById(boardId).orElseThrow(
                () -> new CustomException(ErrorCode.BOARD_NOT_FOUND)
        );

        Page<Post> posts =postRepository.findAllByPostAndBoard(boardId,
                PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")))).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );


        return PageResponseDto.builder()
                .title(title)
                .dtoList(posts.map(AllPostsResponseDto::new).toList())
                .boardTitle(title)
                .page(page)
                .totalPages(posts.getTotalPages())
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
