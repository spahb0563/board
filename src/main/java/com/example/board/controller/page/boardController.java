package com.example.board.controller.page;

import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.PaginationDto;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class boardController {

    private final PostService postService;

    @GetMapping("{categoryType}")
    public String post(@PathVariable String categoryType, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        if(CategoryType.contains(categoryType.toUpperCase())) {
            PaginationDto paginationDto = postService.readAllByCategoryType(CategoryType.valueOf(categoryType.toUpperCase()), pageable);
            model.addAttribute("category", CategoryType.valueOf(categoryType.toUpperCase()));
            model.addAttribute("postList", paginationDto.getData());
            model.addAttribute("page", paginationDto.getPagination());
            return "board";
        }
        return "index";
    }

    @GetMapping("{categoryType}/write")
    public String write(@PathVariable String categoryType, Model model) {
        model.addAttribute("category", categoryType);
        return "write";
    }

    @GetMapping("post/{id}")
    public String post(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.read(id));
        return "post";
    }
}
