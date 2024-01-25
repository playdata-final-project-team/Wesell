package com.wesell.boardservice.domain.repository;

import com.wesell.boardservice.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CustomCommentRepository {
    Optional<List<Comment>> findCommentByPostId(Long postId);
}
