package com.example.board.controller.page;

import com.example.board.config.auth.LoginUser;
import com.example.board.config.auth.dto.SessionUser;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class searchController {

    private final PostService postService;


//    @GetMapping("{category}/")
//    public String searchAll(@LoginUser SessionUser user, @RequestParam("keyword") String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
//        model.addAttribute(postService.searchAll(keyword, pageable));
//
//        return "search";
//    }
}
