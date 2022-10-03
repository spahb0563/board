package com.example.board.controller.page;


import com.example.board.config.auth.dto.SessionUser;
import com.example.board.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor //생성자를 통한 의존성 주입
@Controller
public class IndexController {

    private final CategoryService categoryService;
    private final HttpSession httpSession;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("categoryList", categoryService.findAllCategoryRecentPost());
        SessionUser user = (SessionUser) httpSession.getAttribute("users");

        if(user != null) {
            model.addAttribute("user", user);
        }
        return "index";
    }
}
