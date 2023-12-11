package com.wesell.dealservice.controller;

import com.wesell.dealservice.domain.entity.Category;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.service.DealServiceImpl;
import com.wesell.dealservice.service.FileUploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class DealController {

    private final DealServiceImpl dealService;
    private final FileUploadService uploadService;

    /**
     * @param requestDto
     * @return 판매글 저장
     */
    @PostMapping("post")
    public ResponseEntity<?> uploadDealPost(@Valid @RequestBody UploadDealPostRequestDto requestDto) {
        return new ResponseEntity<>(dealService.createDealPost(requestDto), HttpStatus.CREATED);
    }

    /**
     *
     * @param postId & file
     * @return postId와 이미지 url 저장
     * @throws IOException
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("postId") Long postId, @RequestParam("file") MultipartFile file) throws IOException {
        uploadService.saveImageUrl(postId, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * @param postId 요청
     * @return 상세글 보기 (제목, 생성날짜, 가격, 상세설명, 링크, 작성자 닉네임)
     */
    @GetMapping("post")
    public ResponseEntity<?> getPostInfo(@Valid @RequestParam("id") Long postId) {
        return new ResponseEntity<>(dealService.getPostInfo(postId), HttpStatus.OK);
    }

    /**
     * @param requestDto & postId
     * @return 게시글 수정
     */
    @PutMapping("edit")
    public ResponseEntity<?> editPost(@Valid @RequestBody EditPostRequestDto requestDto, @RequestParam("id") Long postId) {
        return new ResponseEntity<>(dealService.editPost(requestDto, postId),HttpStatus.OK);
    }

    /**
     * @param uuid & postId
     * @return 상태 변경(판매 완료)
     */
    @PutMapping("complete")
    public ResponseEntity<?> changePostStatus(@Valid @RequestParam("uuid") String uuid, @RequestParam("id") Long postId) {
        dealService.changePostStatus(uuid, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param uuid & postId
     * @return 게시글 논리 삭제
     */
    @PutMapping("delete")
    public ResponseEntity<?> deletePost(@Valid @RequestParam("uuid") String uuid,  @RequestParam("id") Long postId) {
        dealService.deletePost(uuid, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param uuid
     * @return 나의 판매 내역 (판매 완료, 판매 중) -> 삭제된 내역은 없음
     */
    @GetMapping("list")
    public ResponseEntity<?> getMyPostList(@RequestParam("uuid") String uuid, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        return new ResponseEntity<>(dealService.getMyPostList(uuid, page),HttpStatus.OK);
    }

    /**
     * @return 판매중인 게시글
     */
    @GetMapping("main")
    public ResponseEntity<?> getMainInfo(@RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(dealService.getDealPostLists(page), HttpStatus.OK);
    }

    @GetMapping("category")
    public ResponseEntity<?> findAllByCategory(@RequestParam("category")Long categoryId, @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(dealService.findByCategory(categoryId, page), HttpStatus.OK);
    }

    @GetMapping("title")
    public ResponseEntity<?> findAllByTitle(@RequestParam("title") String title, @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(dealService.findByTitle(title, page), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> findAllByCategoryAndTitle(@RequestParam("category")Long categoryId, @RequestParam("title") String title
                                        , @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(dealService.findByCategoryAndTitle(categoryId, title, page), HttpStatus.OK);
    }
}
