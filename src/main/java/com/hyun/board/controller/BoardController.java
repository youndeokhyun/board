package com.hyun.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BoardController {

    @GetMapping("/")
    public String mainPage(){
        return "board/index";
    }

    @GetMapping("/write")
    public String write(){
        return "board/write";
    }

}
