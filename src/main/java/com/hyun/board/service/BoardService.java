package com.hyun.board.service;

import com.hyun.board.domain.repository.BoardRepository;
import com.hyun.board.dto.BoardDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class BoardService {

    private BoardRepository boardRepository;

    @Transactional
    public Long savaWrite(BoardDTO boardDTO){
        return boardRepository.save(boardDTO.toEntity()).getId();
    }

}
