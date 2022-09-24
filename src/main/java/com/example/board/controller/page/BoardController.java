package com.example.board.controller.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("{categoryType}")
    public String board(@PathVariable String categoryType) {

        return "board";
    }
}
