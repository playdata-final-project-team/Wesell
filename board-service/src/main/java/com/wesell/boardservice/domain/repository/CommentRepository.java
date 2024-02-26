package com.wesell.boardservice.domain.repository;

import com.wesell.boardservice.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {

    @Query("select c from Comment c where c.post.id = :postId order by c.createdAt asc")
    Page<Comment> findAllByPostId(@Param("postId") Long postId, Pageable pageable);

    @Query("select c from Comment c left join fetch c.parent where c.id = :id")
    Optional<Comment> findCommentByIdWithParent(@Param("id") Long id);
}
