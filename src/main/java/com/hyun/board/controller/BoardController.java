package com.hyun.board.controller;

import com.hyun.board.dto.BoardDTO;
import com.hyun.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@AllArgsConstructor
public class BoardController {

    private BoardService boardService;

    // 리스트 페이지
    @GetMapping("/list")
    public String list(Model model , @RequestParam(value="page" , defaultValue="1") Integer pageNum){
        //list 페이지에 글 출력
        List<BoardDTO> boardList = boardService.getBoardlist(pageNum);
        model.addAttribute("boardList" , boardList);

        // 글목록 페이징
        Integer[] pageList = boardService.getPageList(pageNum);
        model.addAttribute("pageList" , pageList);

        return "board/list";
    }


    // 글쓰기 페이지
    @GetMapping("/write")
    public String write(){
        return "board/write";
    }

    //글 저장
    @PostMapping("/write")
    public String write(BoardDTO boardDTO){
        boardService.savaWrite(boardDTO);

        return "redirect:/list";
    }

    //상세 페이지
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Long id, Model model ){

        BoardDTO boardDTO = boardService.getWrite(id);
        boardService.updateCount(id);
        model.addAttribute("boardDto", boardDTO);


        return  "board/view";
    }

    // 수정페이지
    @GetMapping("/write/edit/{id}")
    public String edit(@PathVariable("id") Long id , Model model){
        BoardDTO boardDTO = boardService.getWrite(id);

        model.addAttribute("boardDTO" , boardDTO);
        return "board/update";
    }

    // 수정한걸 넘겨주고 결과확인
    @PutMapping("/write/edit/{id}")
    public String update(BoardDTO boardDto){
        boardService.savaWrite(boardDto);
        return "redirect:/view/{id}";
    }
    // 삭제후 리스트 페이지로 복귀
    @PostMapping("/write/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.deleteWrite(id);
        return "redirect:/list";
    }

    //페이징
    @GetMapping("/list/search")
    public String search(@RequestParam(value="keyword") String keyword , Model model){
        List<BoardDTO> boardDTOList = boardService.searchContent(keyword);

        model.addAttribute("boardList" , boardDTOList );
        return "/board/list";
    }

    //카운트


}
