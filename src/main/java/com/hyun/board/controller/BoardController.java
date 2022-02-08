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
    public String list(Model model){
        List<BoardDTO> boardList = boardService.getBoardlist();

        model.addAttribute("boardList" , boardList);
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

        return "redirect:/board/list";
    }

    //상세 페이지
    @GetMapping("/view/{no}")
    public String view(@PathVariable("no") Long no, Model model){
        BoardDTO boardDTO = boardService.getWrite(no);

        model.addAttribute("boardDto", boardDTO);
        return  "board/view";
    }

    // 수정페이지
    @GetMapping("/write/edit/{no}")
    public String edit(@PathVariable("no") Long no , Model model){
        BoardDTO boardDTO = boardService.getWrite(no);

        model.addAttribute("boardDTO" , boardDTO);
        return "board/update";
    }

    // 수정한걸 넘겨주고 결과확인
    @PutMapping("/write/edit/{no}")
    public String update(BoardDTO boardDto){
        boardService.savaWrite(boardDto);
        return "redirect:/view/{no}";
    }
    // 삭제후 리스트 페이지로 복귀
    @PostMapping("/write/{no}")
    public String delete(@PathVariable("no") Long no){
        boardService.deleteWrite(no);
        return "redirect:/list";
    }

    //페이징
    @GetMapping("/list/search")
    public String search(@RequestParam(value="keyword") String keyword , Model model){
        List<BoardDTO> boardDTOList = boardService.searchContent(keyword);

        model.addAttribute("boardList" , boardDTOList );
        return "/board/list";
    }

}
