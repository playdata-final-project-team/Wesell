package com.wesell.dealservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wesell.dealservice.domain.dto.request.ChangePostRequestDto;
import com.wesell.dealservice.domain.dto.request.UploadDealPostRequestDto;
import com.wesell.dealservice.domain.dto.request.EditPostRequestDto;
import com.wesell.dealservice.domain.repository.read.ViewDao;
import com.wesell.dealservice.global.response.error.ErrorCode;
import com.wesell.dealservice.service.DealServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2")
public class DealController {

    private final DealServiceImpl dealService;
    private final ViewDao viewDao;

    /**
     *
     * @param requestDto
     * @return
     */
    @PostMapping( "/upload")
    public ResponseEntity<?> createProduct(@RequestBody UploadDealPostRequestDto requestDto) {
        return new ResponseEntity<>(dealService.createDealPost(requestDto), HttpStatus.CREATED);
    }

    /**
     * @param postId 요청
     * @return 상세글 보기 (제목, 생성날짜, 가격, 상세설명, 작성자 닉네임, 작성자 판매횟수, 이미지)
     */
    @GetMapping("post")
    public ResponseEntity<?> getPostInfo(@RequestParam("id") Long postId) {
        return new ResponseEntity<>(dealService.getPostInfo(postId), HttpStatus.OK);
    }

    /**
     * @param requestDto & postId
     * @return 게시글 수정
     */
    @PutMapping("edit")
    public ResponseEntity<?> editPost(@Valid @RequestBody EditPostRequestDto requestDto) throws JsonProcessingException {
        dealService.publishCreateItemMessage(requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("edit")
    public ResponseEntity<?> editPostConfirm(@Valid @RequestBody EditPostRequestDto requestDto) {

        return new ResponseEntity<>(dealService.CompareWithUpdated(requestDto), HttpStatus.OK);
    }

    /**
     * @param requestDto & postId
     * @return 상태 변경(판매 완료)
     */
    @PutMapping("complete")
    public ResponseEntity<?> changePostStatus(@Valid @RequestBody ChangePostRequestDto requestDto) throws JsonProcessingException {
        dealService.publishCreateItemMessage(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("status/{productId}")
    public ResponseEntity<String> getSaleStatus(@PathVariable Long productId){
        return new ResponseEntity<>(viewDao.searchById(productId).getSaleStatus().toString(),HttpStatus.OK);
    }

    /**
     * @param productId
     * @return 게시글 논리 삭제
     */
    @PutMapping("delete")
    public ResponseEntity<?> deletePost(@RequestParam("id") Long productId) {
        dealService.deletePost(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param idArray
     * @return 게시글 일괄 삭제(논리)
     */
    @PutMapping("checked/delete")
    public ResponseEntity<?> deletePostList(@RequestBody Long[] idArray){
        dealService.deletePosts(idArray);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param uuid
     * @return 나의 판매 내역 (판매 완료, 판매 중) -> 삭제된 내역은 없음
     */
    @GetMapping("list")
    public ResponseEntity<?> getMyPostList(@RequestParam("uuid") String uuid, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        return new ResponseEntity<>(dealService.getMyPostList(uuid, page-1),HttpStatus.OK);
    }

    /**
     * @return 판매중인 게시글
     */
    @GetMapping("main")
    public ResponseEntity<?> getMainInfo(@RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(dealService.getDealPostLists(page-1), HttpStatus.OK);
    }

    @GetMapping("main/category")
    public ResponseEntity<?> findAllByCategory(@RequestParam("category")Long categoryId, @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(dealService.findByCategory(categoryId, page-1), HttpStatus.OK);
    }

    @GetMapping("main/title")
    public ResponseEntity<?> findAllByTitle(@RequestParam("title") String title, @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(dealService.findByTitle(title, page-1), HttpStatus.OK);
    }

    @GetMapping("price")
    public ResponseEntity<?> getPriceByPostId(@RequestParam("productId") Long productId) {
        Long price = 0L;

        try {
            price = viewDao.searchPriceById(productId);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(price, ErrorCode.NO_PRICE_RESEARCH.getStatus());
        }

        return new ResponseEntity<>(price, HttpStatus.OK);
    }

}

