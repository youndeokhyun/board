package com.hyun.board.domain.repository;

import com.hyun.board.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity , Long> {


    // findByTitleContaining  By이루는 sql where 조건걸에 대응 되는것
    //  Containing은 like 검색이 된다 앞에 title이 있으므로 제목 검색 %{keyword}%  표현
    List<BoardEntity>  findByTitleContaining(String keyword);

    // 카운트 증가 쿼리
    @Modifying
    @Query("update BoardEntity b set b.count = b.count +1 where b.id = :id")
    int updateCount(Long id);

}
