package com.wesell.boardservice.domain.repository;

import com.wesell.boardservice.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);
    @Query("select b.title from Board b where b.id =:id")
    Optional<String> findTitleByBoardId(@Param("id")Long id);
}
