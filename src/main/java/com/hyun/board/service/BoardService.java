package com.hyun.board.service;

import com.hyun.board.domain.entity.BoardEntity;
import com.hyun.board.domain.repository.BoardRepository;
import com.hyun.board.dto.BoardDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


    //수정시 원본? 데이터 가져오는 메소드
    @Transactional
    public BoardDTO getWrite(Long id){
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        return BoardDTO.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }

    @Transactional
    public void deleteWrite(Long id){
        boardRepository.deleteById(id);
    }


    //글 목록
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
     // Entity를 Dto로 변환하는 작업 중복처리 개선
    private BoardDTO convertEntityToDto(BoardEntity boardEntity){
        return  BoardDTO.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }

    //페이징 서비스
    private static final int BLOCK_NUM_COUNT = 5;   // 한 블럭당 존재하는 페이지 번호
    private static final int PAGE_WRITE_COUNT = 10;  // 한 페이지당 존재하는 게시글수

    //list 출력
    @Transactional
    public List<BoardDTO> getWriteList(Integer pageNum){
        /*Pageable 인터페이스를 구현한 클래스(PageRequest.of())를 전달하여 페이징
        PageRequest.of("pageNum -1 =현재 페이지 번호 - 1"을 계산한 값(실제 페이지 번호와 SQL 조회시 사용되는 limit은 다르기 때문)
                        PAGE_WRITE_COUNT =  위에 변수 대로 몇개의 글을 가져올것인가
                        Sort.by(Sort.Direction.ASC, "createDate") = 정렬 방식 createDate 로 설정하여 ASC 오른 차순으로 정렬
                        */
        Page<BoardEntity> page =boardRepository.findAll(PageRequest.of(pageNum -1 , PAGE_WRITE_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));

        List<BoardEntity> boardEntities = page.getContent(); // 전체 게시글수
        List<BoardDTO> boardDTOList = new ArrayList<>();

        // boardEntities 총 게시글 수만큼 변수명 boardEntity 담아 boardDTOList 새로운 ArrayList<>() 에 추가
        for(BoardEntity boardEntity : boardEntities){
            boardDTOList.add(this.convertEntityToDto(boardEntity));

        }
        return boardDTOList;
    }
    //전체 게시글 개수를 가져온다
    @Transactional
    public Long getBoardCount(){
        return boardRepository.count();
    }
    // 페이징 로직
    public Integer[] getPageList(Integer curPageNum){
        Integer[] pageList  = new Integer[BLOCK_NUM_COUNT];

        // 총 게시글 갯수
        Double writeTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 기준으로 계산한 마지막 블럭 번호 계산(올림으로)
        Integer totalLastPageNum  = (int)(Math.ceil(writeTotalCount/PAGE_WRITE_COUNT));

        curPageNum = (curPageNum <= 2) ? 1 : curPageNum - 1;


        //현제 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_NUM_COUNT-1)
                ? curPageNum + BLOCK_NUM_COUNT - 1 :totalLastPageNum;

        for(int val = curPageNum , idx = 0; val <= blockLastPageNum; val++ , idx++){
            pageList[idx] = val;
        }

        return pageList;
    }
}
