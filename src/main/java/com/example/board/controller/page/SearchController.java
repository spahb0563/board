package com.example.board.controller.page;

import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class SearchController {

    private final PostService postService;


//    @GetMapping("{category}/")
//    public String searchAll(@LoginUser SessionUser user, @RequestParam("keyword") String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
//        model.addAttribute(postService.searchAll(keyword, pageable));
//
//        return "search";
//    }
}
