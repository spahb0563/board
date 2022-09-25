package com.example.board.controller.page;


import com.example.board.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor //생성자를 통한 의존성 주입
@Controller
public class IndexController {

    private final CategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("categoryList", categoryService.findAllCategoryRecentPost());
        return "index";
    }
}
