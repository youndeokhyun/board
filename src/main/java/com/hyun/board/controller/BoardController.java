package com.hyun.board.controller;

import com.hyun.board.dto.BoardDTO;
import com.hyun.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@AllArgsConstructor
public class BoardController {

    private BoardService boardService;

    @GetMapping("/")
    public String mainPage(){
        return "board/index";
    }

    @GetMapping("/write")
    public String write(){
        return "board/write";
    }

    @PostMapping("/write")
    public String write(BoardDTO boardDTO){
        boardService.savaWrite(boardDTO);

        return "redirect:/";
    }
}
