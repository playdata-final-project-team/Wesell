package com.wesell.boardservice.domain.repository;

import com.wesell.boardservice.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
