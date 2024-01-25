package com.wesell.boardservice.domain.repository;

import com.wesell.boardservice.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

    @Query("select p from Post p")
    Optional<Page<Post>> findAllByPost(Pageable pageable);
}
