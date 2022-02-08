package com.hyun.board.service;

import com.hyun.board.domain.entity.BoardEntity;
import com.hyun.board.domain.repository.BoardRepository;
import com.hyun.board.dto.BoardDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {

    private BoardRepository boardRepository;
    //게시판 글 수정/저장  메소드
    @Transactional
    public Long savaWrite(BoardDTO boardDTO){
        return boardRepository.save(boardDTO.toEntity()).getId();
    }
    //게씨판 리스트 뿌려주는 메소드
    @Transactional
    public List<BoardDTO> getBoardlist(){
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for( BoardEntity boardEntity : boardEntityList){
           BoardDTO boardDTO = BoardDTO.builder()
                   .id(boardEntity.getId())
                   .title(boardEntity.getTitle())
                   .count(boardEntity.getCount())
                   .createdDate(boardEntity.getCreatedDate())
                   .build();
           boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }
    //수정시 원본? 데이터 가져오는 메소드
    @Transactional
    public BoardDTO getWrite(Long id){
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        BoardDTO boardDTO = BoardDTO.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdDate(boardEntity.getCreatedDate())
                .build();
                return boardDTO;
    }

    @Transactional
    public void deleteWrite(Long id){
        boardRepository.deleteById(id);
    }

    //Repository에서 검색 결과를 받아와 비즈니스 로직을 실행하는 함수입니다.
    //controller <--> Setvice 간에는 Dto 객채로 전달하는것이 좋다
    @Transactional
    public List<BoardDTO> searchContent (String keyword){

        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDTO> boardDTOList = new ArrayList<>();

        if(boardEntities.isEmpty()) return boardDTOList;

        for(BoardEntity boardEntity : boardEntities){
            boardDTOList.add(this.convertEntityToDto(boardEntity));
        }
        return boardDTOList;
    }

    private BoardDTO convertEntityToDto(BoardEntity boardEntity){
        return  BoardDTO.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
}
