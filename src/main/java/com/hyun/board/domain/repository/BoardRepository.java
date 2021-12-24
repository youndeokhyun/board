package com.hyun.board.domain.repository;

import com.hyun.board.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity , Long> {
}
