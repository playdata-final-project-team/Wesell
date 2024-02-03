package com.wesell.boardservice.domain.repository;

import com.wesell.boardservice.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);
}
